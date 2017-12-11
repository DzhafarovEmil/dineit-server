package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.Food;
import emil.dzhafarov.dineit.model.FoodCompany;
import emil.dzhafarov.dineit.service.FoodCompanyService;
import emil.dzhafarov.dineit.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FoodController {

    @Autowired
    FoodService foodService;
    @Autowired
    FoodCompanyService foodCompanyService;

    @RequestMapping(value = "/food", method = RequestMethod.GET)
    public ResponseEntity<Collection<Food>> getAllFoods(@RequestParam("food_company_id") Long foodCompanyId) {
        FoodCompany foodCompany = foodCompanyService.findById(foodCompanyId);

        List<Food> foods = new LinkedList<>();

        if (foodCompany != null) {
            foods = foodCompany.getAvailableFoods();
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @RequestMapping(value = "/food/{id}", method = RequestMethod.GET)
    public ResponseEntity<Food> findFoodById(@PathVariable("id") Long id) {
        Food food = foodService.findById(id);

        if (food == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @RequestMapping(value = "/food/", method = RequestMethod.POST)
    public ResponseEntity<Long> createFood(@RequestBody Food food, Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

        Long id = 0L;

        if (foodCompany != null) {

            if (foodCompany.getAvailableFoods().contains(food)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                id = foodService.create(food);
                food.setId(id);
                foodCompany.getAvailableFoods().add(food);
                foodCompanyService.update(foodCompany);
            }
        }

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/food/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Long> updateFood(@PathVariable("id") Long id, @RequestBody Food food, Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

        if (foodCompany != null) {
            for (Food f : foodCompany.getAvailableFoods()) {
                if (f.equals(food)) {
                    f.setName(food.getName());
                    f.setType(food.getType());
                    f.setDescription(food.getDescription());
                    f.setPrice(food.getPrice());
                    f.setImageURL(food.getImageURL());
                    break;
                }
            }

            foodCompanyService.update(foodCompany);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/food/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteFood(@PathVariable("id") Long id, Principal principal) {
        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());

        if (foodCompany != null) {
            for (Food f : foodCompany.getAvailableFoods()) {
                if (f.getId().equals(id)) {
                    foodCompany.getAvailableFoods().remove(f);
                    foodService.deleteById(id);
                    foodCompanyService.update(foodCompany);
                    return new ResponseEntity<>(id, HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
