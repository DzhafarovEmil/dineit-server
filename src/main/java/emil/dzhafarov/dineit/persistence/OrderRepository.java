package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.Customer;
import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);

    List<Order> findByFoodCompany(FoodCompany foodCompany);
}
