package emil.dzhafarov.dineit.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import emil.dzhafarov.dineit.model.*;
import emil.dzhafarov.dineit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.*;

import static java.lang.System.in;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final String SYSTEM_USER_CUSTOMER = "customer";

    private static final String SYSTEM_USER_FOOD_COMPANY = "food-company";

    @Autowired
    private OrderService orderService;
    @Autowired
    private FoodCompanyService foodCompanyService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private FridgeService fridgeService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("user") String userType,
                                                    @RequestParam("sort") Integer sort,
                                                    Principal principal) {
        if (SYSTEM_USER_CUSTOMER.equals(userType)) {
            Customer customer = customerService.findByUsername(principal.getName());
            if (customer != null) {
                List<Order> orders = new LinkedList<>();
                orders.addAll(orderService.findByCustomer(customer));
                sortList(sort, orders);
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
        }

        if (SYSTEM_USER_FOOD_COMPANY.equals(userType)) {
            FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

            if (foodCompany != null) {
                List<Order> orders = new LinkedList<>();
                orders.addAll(orderService.findByFoodCompany(foodCompany));
                sortList(sort, orders);
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/order/{order_id}/foods", method = RequestMethod.GET)
    public ResponseEntity<List<Food>> getAllFoodsInOrder(@PathVariable("order_id") Long orderId) {
        Order order = orderService.findById(orderId);

        if (order != null) {
            List<Food> foods = new LinkedList<>();
            foods.addAll(order.getFoods());
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/create-order/", method = RequestMethod.POST)
    public ResponseEntity<QRCode> createOrder(@RequestParam("food_company_id") Long foodCompanyId,
                                              @RequestParam("fridge_id") Long fridgeId,
                                              @RequestBody Order order,
                                              Principal principal) throws IOException, WriterException {
        FoodCompany foodCompany = foodCompanyService.findById(foodCompanyId);
        Customer customer = customerService.findByUsername(principal.getName());
        Fridge fridge = fridgeService.findById(fridgeId);

        if (foodCompany != null && customer != null && fridge != null) {
            if (orderService.isExist(order)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            order.setFridge(fridge);
            order.setCustomer(customer);
            order.setFoodCompany(foodCompany);
            order.setOrderedTime(System.currentTimeMillis());
            Long id = orderService.create(order);
            order.setId(id);

            byte[] bytes = getQRCodeImage(encodeToBase64(order.toString().getBytes()));
            QRCode objCode = new QRCode(bytes);
            objCode.setId(qrCodeService.create(objCode));
            order.setQrCode(objCode);
            orderService.update(order);


            return new ResponseEntity<>(objCode, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/order/validate", method = RequestMethod.GET)
    public ResponseEntity<Order> validate(@RequestParam("qr_code") String qrCode,
                                              Principal principal) throws IOException, WriterException {
        Fridge fridge = fridgeService.findByUsername(principal.getName());

        if (fridge != null) {
            qrCode = decodeFromBase64(qrCode);
            System.out.println("QR CODE ========> " + decodeFromBase64(new String(qrCode.getBytes(), "UTF-8")));
            Long orderId = Long.parseLong(qrCode.substring(qrCode.indexOf("id") + 3, qrCode.indexOf(",")));
            Order order = orderService.findById(orderId);

            if (order != null && order.getStatus() != OrderStatus.RECEIVED) {
                order.setStatus(OrderStatus.RECEIVED);
                orderService.update(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateOrder(@PathVariable("id") Long id, @RequestBody Order order, Principal principal) {
        Order o = orderService.findById(id);
        if (o != null && principal.getName().equals(o.getFoodCompany().getUsername())) {
            o.setCustomer(order.getCustomer());
            o.setFoods(order.getFoods());
            o.setFridge(order.getFridge());
            o.setOrderedTime(order.getOrderedTime());
            o.setStatus(order.getStatus());
            orderService.update(o);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private String convert(byte[] bytes) throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        InputStream in = new ByteArrayInputStream(bytes);
        InputStreamReader reader = new InputStreamReader(in, "UTF-8");

        int charsRead;
        while ((charsRead = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, charsRead & 0xff);
        }

        return builder.toString();
    }

    private byte[] getQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    private void sortList(Integer sort, List<Order> orders) {
        if (sort.equals(1)) {
            orders.sort(((o1, o2) -> (int) (o2.getOrderedTime() - o1.getOrderedTime())));
        }
        if (sort.equals(0)) {
            orders.sort(((o1, o2) -> (int) (o1.getOrderedTime() - o2.getOrderedTime())));
        }
    }

    private String encodeToBase64(byte[] data) {
        return new String(Base64.getEncoder().encode(data));
    }

    private String decodeFromBase64(String result) {
        return new String(Base64.getDecoder().decode(result.getBytes()));
    }
}
