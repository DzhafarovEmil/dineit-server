package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{}
