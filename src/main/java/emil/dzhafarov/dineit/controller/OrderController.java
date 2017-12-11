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
                return new ResponseEntity<>(orderService.findByCustomer(customer), HttpStatus.OK);
            }
        }

        if (SYSTEM_USER_FOOD_COMPANY.equals(userType)) {
            FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

            if (foodCompany != null) {
                return new ResponseEntity<>(orderService.findByFoodCompany(foodCompany), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/create-order/", method = RequestMethod.POST)
    public ResponseEntity<Long> createOrder(@RequestBody Order order, Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findById(order.getFoodCompany().getId());
        Customer customer = customerService.findByUsername(principal.getName());

        if (foodCompany != null && customer != null) {
            if (orderService.isExist(order)) {
                return new ResponseEntity<>(-1L, HttpStatus.CONFLICT);
            }

            order.setCustomer(customer);
            order.setFoodCompany(foodCompany);
            Long id = orderService.create(order);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
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
}
