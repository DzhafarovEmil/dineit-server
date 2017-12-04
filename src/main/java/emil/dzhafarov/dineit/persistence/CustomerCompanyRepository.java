package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.CustomerCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCompanyRepository extends CrudRepository<CustomerCompany, Long> {}
