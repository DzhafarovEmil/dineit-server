package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.CustomerCompany;
import emil.dzhafarov.dineit.service.CustomerCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerCompanyController {

    @Autowired
    private CustomerCompanyService customerCompanyService;

    @RequestMapping(value = "/customer-company", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerCompany>> getAllCustomerCompanies() {
        List<CustomerCompany> customerCompanies = customerCompanyService.getAll();

        if (customerCompanies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customerCompanies, HttpStatus.OK);
    }

    @RequestMapping(value = "/customer-company/{id}", method = RequestMethod.GET)
    public ResponseEntity<CustomerCompany> findCustomerCompanyById(@PathVariable("id") Long id) {
        CustomerCompany customerCompany = customerCompanyService.findById(id);

        if (customerCompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerCompany, HttpStatus.OK);
    }

    @RequestMapping(value = "/customer-company/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCustomerCompany(@RequestBody CustomerCompany customerCompany) {
        if (customerCompanyService.isExist(customerCompany)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        customerCompanyService.create(customerCompany);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer-company/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCustomerCompany(@PathVariable("id") Long id,
                                                      @RequestBody CustomerCompany customerCompany) {
        CustomerCompany currentCustomerCompany = customerCompanyService.findById(id);

        if (currentCustomerCompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCustomerCompany.setLocalCustomers(customerCompany.getLocalCustomers());
        currentCustomerCompany.setLocalFridges(customerCompany.getLocalFridges());

        customerCompanyService.update(currentCustomerCompany);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/customer-company/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCustomerCompany(@PathVariable("id") Long id) {
        CustomerCompany customerCompany = customerCompanyService.findById(id);

        if (customerCompany == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerCompanyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
