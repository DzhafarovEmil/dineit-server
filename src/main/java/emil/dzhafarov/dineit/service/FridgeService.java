package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.model.Fridge;
import emil.dzhafarov.dineit.persistence.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FridgeService implements RestContract<Fridge>{

    @Autowired
    private FridgeRepository repository;

    @Override
    public List<Fridge> getAll() {
        List<Fridge> fridges = new LinkedList<>();
        repository.findAll().forEach(fridges::add);

        return fridges;
    }

    @Override
    public Fridge findById(Long id) {
        return repository.findOne(id);
    }

    public Fridge findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean isExist(Fridge obj) {
        return repository.exists(obj.getId());
    }

    @Override
    public Long create(Fridge obj) {
        return repository.save(obj).getId();
    }

    @Override
    public void update(Fridge obj) {
        repository.save(obj);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }
}
