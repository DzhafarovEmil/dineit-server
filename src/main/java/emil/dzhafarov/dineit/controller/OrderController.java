package emil.dzhafarov.dineit.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.model.Food;
import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.model.Order;
import emil.dzhafarov.dineit.service.CustomerService;
import emil.dzhafarov.dineit.service.FoodCompanyService;
import emil.dzhafarov.dineit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

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

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    private void sortList(Integer sort, List<Order> orders) {
        if (sort.equals(1)) {
            orders.sort(((o1, o2) -> (int) (o2.getOrderedTime() - o1.getOrderedTime())));
        }
        if (sort.equals(0)) {
            orders.sort(((o1, o2) -> (int) (o1.getOrderedTime() - o2.getOrderedTime())));
        }
    }

    @RequestMapping(value = "/order/{order_id}/foods", method = RequestMethod.GET)
    public ResponseEntity<List<Food>> getAllFoodsInOrder(@PathVariable("order_id") Long orderId) {
        Order order = orderService.findById(orderId);

        if (order != null) {
            List<Food> foods = new LinkedList<>();
            foods.addAll(order.getFoods());
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/create-order/", method = RequestMethod.POST)
    public ResponseEntity<byte[]> createOrder(@RequestParam("food_company_id") Long foodCompanyId,
                                              @RequestBody Order order,
                                              Principal principal) throws IOException, WriterException {
        FoodCompany foodCompany = foodCompanyService.findById(foodCompanyId);
        Customer customer = customerService.findByUsername(principal.getName());

        if (foodCompany != null && customer != null) {
            if (orderService.isExist(order)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            order.setCustomer(customer);
            order.setFoodCompany(foodCompany);
            Long id = orderService.create(order);
            order.setId(id);

            byte[] qrCode = getQRCodeImage(order.toString(), 400, 400);
            return new ResponseEntity<>(qrCode, HttpStatus.CREATED);
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

    private byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
