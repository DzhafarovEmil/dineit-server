package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CustomerService implements RestContract<Customer> {

    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new LinkedList<>();
        repository.findAll().forEach(customers::add);

        return customers;
    }

    @Override
    public Customer findById(Long id) {
        return repository.findOne(id);
    }

    public Customer findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean isExist(Customer obj) {
        return repository.findByUsername(obj.getUsername()) != null;
    }

    @Override
    public Long create(Customer obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(Customer obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
