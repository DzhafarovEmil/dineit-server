package emil.dzhafarov.dineit.controller;

import emil.dzhafarov.dineit.model.Address;
import emil.dzhafarov.dineit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAll();

        if (addresses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> findAddressById(@PathVariable("id") Long id) {
        Address address = addressService.findById(id);

        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @RequestMapping(value = "/address/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAddress(@RequestBody Address address) {
        if (addressService.isExist(address)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        addressService.create(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAddress(@PathVariable("id") Long id, @RequestBody Address address) {
        Address currentAddress = addressService.findById(id);

        if (currentAddress == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentAddress.setBuilding(address.getBuilding());
        currentAddress.setCity(address.getCity());
        currentAddress.setCountry(address.getCountry());
        currentAddress.setDistrict(address.getDistrict());
        currentAddress.setStreet(address.getStreet());

        addressService.update(currentAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id) {
        Address address = addressService.findById(id);

        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        addressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
