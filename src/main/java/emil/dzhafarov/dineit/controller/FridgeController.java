package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.Fridge;
import emil.dzhafarov.dineit.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FridgeController {

    @Autowired
    private FridgeService fridgeService;

    @RequestMapping(value = "/fridge", method = RequestMethod.GET)
    public ResponseEntity<List<Fridge>> getAllFridges() {
        List<Fridge> fridges = fridgeService.getAll();

        if (fridges.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(fridges, HttpStatus.OK);
    }

    @RequestMapping(value = "/fridge/{id}", method = RequestMethod.GET)
    public ResponseEntity<Fridge> findFridgeById(@PathVariable("id") Long id) {
        Fridge fridge = fridgeService.findById(id);

        if (fridge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fridge, HttpStatus.OK);
    }

    @RequestMapping(value = "/fridge/", method = RequestMethod.POST)
    public ResponseEntity<Void> createFridge(@RequestBody Fridge fridge) {
        if (fridgeService.isExist(fridge)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        fridgeService.create(fridge);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/fridge/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateFridge(@PathVariable("id") Long id, @RequestBody Fridge fridge) {
        Fridge currentFridge = fridgeService.findById(id);

        if (currentFridge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentFridge.setCapacity(fridge.getCapacity());
        currentFridge.setFloor(fridge.getFloor());
        currentFridge.setStartDate(fridge.getStartDate());

        fridgeService.update(currentFridge);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/fridge/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFridge(@PathVariable("id") Long id) {
        Fridge fridge = fridgeService.findById(id);

        if (fridge == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        fridgeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
