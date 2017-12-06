package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.model.Order;
import emil.dzhafarov.dineit.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService implements RestContract<Order> {

    @Autowired
    private OrderRepository repository;

    @Override
    public List<Order> getAll() {
        List<Order> orders = new LinkedList<>();
        repository.findAll().forEach(orders::add);

        return orders;
    }

    @Override
    public Order findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean isExist(Order obj) {
        return repository.exists(obj.getId());
    }

    public List<Order> findByCustomer(Customer customer) {
        return repository.findByCustomer(customer);
    }

    public List<Order> findByFoodCompany(FoodCompany foodCompany) {
        return repository.findByFoodCompany(foodCompany);
    }

    @Override
    public Long create(Order obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(Order obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
