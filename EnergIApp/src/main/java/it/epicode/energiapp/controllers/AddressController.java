package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressByID(@PathVariable Long id) {
        Address address = addressService.findAddressById(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.saveAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        Address address = addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address with id: " + id + " has been deleted successfully");
    }
}
