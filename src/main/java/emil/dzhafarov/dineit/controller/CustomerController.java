package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/api/customer", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAll();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/customer/{username}", method = RequestMethod.GET)
    public ResponseEntity<Customer> findCustomerById(@PathVariable("username") String username) {
        Customer customer = customerService.findByUsername(username);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/register-customer/", method = RequestMethod.POST)
    public ResponseEntity<Long> createCustomer(@RequestBody Customer customer) {
        if (customerService.isExist(customer)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        System.out.println(customer);

        Long id = customerService.create(customer);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,
                                            @RequestBody Customer customer) {
        Customer currentCustomer = customerService.findById(id);

        if (currentCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setFirstName(customer.getFirstName());
        currentCustomer.setLastName(customer.getLastName());
        currentCustomer.setPhoneNumber(customer.getPhoneNumber());
        currentCustomer.setUsername(customer.getUsername());
        currentCustomer.setPassword(customer.getPassword());

        customerService.update(currentCustomer);
        return new ResponseEntity<>(currentCustomer, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
