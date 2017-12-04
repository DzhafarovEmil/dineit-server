package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.FoodCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCompanyRepository extends CrudRepository<FoodCompany, Long> {
    FoodCompany findByUsername(String username);
}
