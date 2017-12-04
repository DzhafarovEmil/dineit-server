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

        if (foods.size() == 0) {
            repository.save(new Food("pasta", "food", "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                    "Very tasteful food!", 112.35));
            repository.save(new Food("sandwich", "food", "http://the-toast.net/wp-content/uploads/2015/07/RPLC_Sweet_Onion_Chicken_Teriyaki.jpg",
                    "Good snack for business people!", 32.99));
            repository.save(new Food("pasta", "food", "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                    "Very tasteful food!", 112.35));
            repository.save(new Food("sandwich", "food", "http://the-toast.net/wp-content/uploads/2015/07/RPLC_Sweet_Onion_Chicken_Teriyaki.jpg",
                    "Good snack for business people!", 32.99));
            repository.save(new Food("pasta", "food", "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                    "Very tasteful food!", 112.35));
            repository.save(new Food("sandwich", "food", "http://the-toast.net/wp-content/uploads/2015/07/RPLC_Sweet_Onion_Chicken_Teriyaki.jpg",
                    "Good snack for business people!", 32.99));
            repository.save(new Food("pasta", "food", "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                    "Very tasteful food!", 112.35));
            repository.save(new Food("sandwich", "food", "http://the-toast.net/wp-content/uploads/2015/07/RPLC_Sweet_Onion_Chicken_Teriyaki.jpg",
                    "Good snack for business people!", 32.99));
            repository.save(new Food("pasta", "food", "https://www.thelocal.it/userdata/images/article/69523836b0191608c41d640feead8da2be5462038d3409e1e3900fad039c7fc8.jpg",
                    "Very tasteful food!", 112.35));
            repository.save(new Food("sandwich", "food", "http://the-toast.net/wp-content/uploads/2015/07/RPLC_Sweet_Onion_Chicken_Teriyaki.jpg",
                    "Good snack for business people!", 32.99));
        }

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
