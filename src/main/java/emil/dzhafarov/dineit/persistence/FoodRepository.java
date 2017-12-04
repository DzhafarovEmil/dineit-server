package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

}
