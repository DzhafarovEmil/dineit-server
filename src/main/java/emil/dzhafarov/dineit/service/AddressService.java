package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.Address;
import emil.dzhafarov.dineit.persistence.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AddressService implements RestContract<Address>{

    @Autowired
    private AddressRepository repository;

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new LinkedList<>();
        repository.findAll().forEach(addresses::add);

        return addresses;
    }

    @Override
    public Address findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean isExist(Address obj) {
        return repository.exists(obj.getId());
    }

    @Override
    public Long create(Address obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(Address obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
