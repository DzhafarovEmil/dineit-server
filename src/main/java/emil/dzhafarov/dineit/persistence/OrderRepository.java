package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {}
