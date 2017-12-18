package emil.dzhafarov.dineit.controller;import emil.dzhafarov.dineit.model.FoodCompany;import emil.dzhafarov.dineit.service.FoodCompanyService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import java.security.Principal;import java.time.LocalDate;import java.util.LinkedList;import java.util.List;@RestControllerpublic class FoodCompanyController {    @Autowired    private FoodCompanyService foodCompanyService;    @RequestMapping(value = "/api/food-company", method = RequestMethod.GET)    public ResponseEntity<List<FoodCompany>> getAllFoodCompanies() {        List<FoodCompany> foodCompanies = new LinkedList<>();        foodCompanies.addAll(foodCompanyService.getAll());        return new ResponseEntity<>(foodCompanies, HttpStatus.OK);    }    @RequestMapping(value = "/api/food-company/{username}", method = RequestMethod.GET)    public ResponseEntity<FoodCompany> findFoodCompanyByUsername(@PathVariable("username") String username) {        FoodCompany foodCompany = foodCompanyService.findByUsername(username);        if (foodCompany == null) {            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }        return new ResponseEntity<>(foodCompany, HttpStatus.OK);    }    @RequestMapping(value = "/api/food-company/", method = RequestMethod.GET)    public ResponseEntity<FoodCompany> findFoodCompanyById(@RequestParam(name = "id") Long id) {        FoodCompany foodCompany = foodCompanyService.findById(id);        if (foodCompany == null) {            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }        return new ResponseEntity<>(foodCompany, HttpStatus.OK);    }    @RequestMapping(value = "/register-food-company/", method = RequestMethod.POST)    public ResponseEntity<Long> createFoodCompany(@RequestBody FoodCompany foodCompany) {        if (foodCompanyService.isExist(foodCompany)) {            return new ResponseEntity<>(HttpStatus.CONFLICT);        }        foodCompany.setRegistrationDate(LocalDate.now());        Long id = foodCompanyService.create(foodCompany);        return new ResponseEntity<>(id, HttpStatus.CREATED);    }    @RequestMapping(value = "/api/food-company/{id}", method = RequestMethod.PUT)    public ResponseEntity<Long> updateFoodCompany(@PathVariable("id") Long id,                                                  @RequestBody FoodCompany fc,                                                  Principal principal) {        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());        if (foodCompany != null && id.equals(foodCompany.getId())) {            foodCompany.setName(fc.getName());            foodCompany.setBusinessCode(fc.getBusinessCode());            foodCompany.setEmail(fc.getEmail());            foodCompany.setPhoneNumber(fc.getPhoneNumber());            foodCompany.setOwnerName(fc.getOwnerName());            foodCompany.setAddress(fc.getAddress());            foodCompany.setImageURL(fc.getImageURL());            foodCompany.setSocialNetworkRefs(fc.getSocialNetworkRefs());            foodCompanyService.update(foodCompany);            return new ResponseEntity<>(id, HttpStatus.OK);        }        return new ResponseEntity<>(HttpStatus.NOT_FOUND);    }    @RequestMapping(value = "/api/food-company/{id}", method = RequestMethod.DELETE)    public ResponseEntity<Long> deleteFoodCompany(@PathVariable("id") Long id, Principal principal) {        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());        if (foodCompany != null && id.equals(foodCompany.getId())) {            foodCompanyService.deleteById(id);            return new ResponseEntity<>(id, HttpStatus.OK);        }        return new ResponseEntity<>(HttpStatus.NOT_FOUND);    }    @RequestMapping(value = "/food-company/change-password", method = RequestMethod.GET)    public ResponseEntity<Long> changePassword(@RequestParam(name = "currentPassword") String currentPassword,                                               @RequestParam(name = "newPassword") String newPassword,                                               Principal principal) {        FoodCompany foodCompany = foodCompanyService.findByUsername(principal.getName());        if (foodCompany == null) {            return new ResponseEntity<>(HttpStatus.NOT_FOUND);        }        if (!foodCompany.getPassword().equals(currentPassword)) {            return new ResponseEntity<>(HttpStatus.FORBIDDEN);        }        if (!validatePassword(newPassword)) {            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);        }        foodCompany.setPassword(newPassword);        foodCompanyService.update(foodCompany);        return new ResponseEntity<>(foodCompany.getId(), HttpStatus.OK);    }    private boolean validatePassword(String password) {        return true;    }}