package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.persistence.FoodCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodCompanyService implements RestContract<FoodCompany> {

    @Autowired
    private FoodCompanyRepository repository;

    @Override
    public List<FoodCompany> getAll() {
        List<FoodCompany> foodCompanies = new LinkedList<>();
        repository.findAll().forEach(foodCompanies::add);

        return foodCompanies;
    }

    @Override
    public FoodCompany findById(Long id) {
        return repository.findOne(id);
    }

    public FoodCompany findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean isExist(FoodCompany obj) {
        return repository.exists(obj.getId());
    }

    @Override
    public Long create(FoodCompany obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(FoodCompany obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
