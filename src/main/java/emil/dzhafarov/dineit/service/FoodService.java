package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.Food;
import emil.dzhafarov.dineit.persistence.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class FoodService implements RestContract<Food> {

    @Autowired
    private FoodRepository repository;

    public List<Food> getAll() {
        List<Food> foods = new LinkedList<>();
        repository.findAll().forEach(foods::add);

        return foods;
    }

    public Food findById(Long id) {
        return repository.findOne(id);
    }

    public boolean isExist(Food food) {
        return repository.exists(food.getId());
    }

    public Long create(Food food) {
        return repository.save(food).getId();
    }

    public void update(Food food) {
        repository.save(food);
    }

    public void deleteById(Long id) {
        repository.delete(id);
    }
}
