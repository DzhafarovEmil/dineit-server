package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.CustomerCompany;
import emil.dzhafarov.dineit.persistence.CustomerCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CustomerCompanyService implements RestContract<CustomerCompany> {

    @Autowired
    private CustomerCompanyRepository repository;

    @Override
    public List<CustomerCompany> getAll() {
        List<CustomerCompany> customerCompanies = new LinkedList<>();
        repository.findAll().forEach(customerCompanies::add);

        return customerCompanies;
    }

    @Override
    public CustomerCompany findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean isExist(CustomerCompany obj) {
        return repository.exists(obj.getId());
    }

    @Override
    public Long create(CustomerCompany obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(CustomerCompany obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
