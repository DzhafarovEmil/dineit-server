package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.Fridge;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends CrudRepository<Fridge, Long> {
    Fridge findByUsername(String username);
}
