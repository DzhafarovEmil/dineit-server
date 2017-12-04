package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.model.Order;
import emil.dzhafarov.dineit.service.CustomerService;
import emil.dzhafarov.dineit.service.FoodCompanyService;
import emil.dzhafarov.dineit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("user") String userType, Principal principal) {
        if (SYSTEM_USER_CUSTOMER.equals(userType)) {
            Customer customer = customerService.findByUsername(principal.getName());
            if (customer != null) {
                List<Order> orders = customer.getOrders();
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
        }

        if (SYSTEM_USER_FOOD_COMPANY.equals(userType)) {
            FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

            if (foodCompany != null) {
                List<Order> orders = foodCompany.getOrders();
                return new ResponseEntity<>(orders, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long id) {
        Order order = orderService.findById(id);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/", method = RequestMethod.POST)
    public ResponseEntity<Long> createOrder(@RequestParam("id") Long foodCompanyId,
                                            @RequestBody Order order,
                                            Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findById(foodCompanyId);
        Customer customer = customerService.findByUsername(principal.getName());

        if (foodCompany != null && customer != null) {

            if (orderService.isExist(order)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Long id = orderService.create(order);
            order.setId(id);
            foodCompany.getOrders().add(order);
            foodCompanyService.update(foodCompany);
            customer.getOrders().add(order);
            customerService.update(customer);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateOrder(@PathVariable("id") Long id, @RequestBody Order order, Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

        if (foodCompany != null) {
            for (Order o : foodCompany.getOrders()) {
                if (o.getId().equals(id)) {
                    o.setCustomer(order.getCustomer());
                    o.setFoods(order.getFoods());
                    o.setFridge(order.getFridge());
                    o.setOrderedTime(order.getOrderedTime());
                    o.setStatus(order.getStatus());
                    orderService.update(o);
                    foodCompanyService.update(foodCompany);
                    return new ResponseEntity<>(id, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteOrder(@RequestParam("user") String userType,
                                            @PathVariable("id") Long id,
                                            Principal principal) {

        if (SYSTEM_USER_CUSTOMER.equals(userType)) {
            Customer customer = customerService.findByUsername(principal.getName());

            if (customer != null) {
                boolean result = deleteOrderFromList(customer.getOrders(), id);

                if (result) {
                    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
                }
            }
        }

        if (SYSTEM_USER_FOOD_COMPANY.equals(userType)) {
            FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

            if (foodCompany != null) {
                boolean result = deleteOrderFromList(foodCompany.getOrders(), id);

                if (result) {
                    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Boolean deleteOrderFromList(List<Order> orders, Long id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                orderService.deleteById(id);
                orders.remove(order);
                return true;
            }
        }

        return false;
    }
}
