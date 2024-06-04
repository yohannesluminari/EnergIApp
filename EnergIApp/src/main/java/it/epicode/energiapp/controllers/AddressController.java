package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.exceptions.NoContentException;
import it.epicode.energiapp.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    // GET http://localhost:8080/api/addresses

    @GetMapping
    public ResponseEntity<Page<Address>> getAllAddresses(Pageable pageable) {
        Page<Address> addresses = addressService.findAllAddresses(pageable);
        if (addresses.isEmpty()) {
            throw new NoContentException("No addresses were found.");
        } else {
            ResponseEntity<Page<Address>> responseEntity = new ResponseEntity<>(addresses, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/addresses/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressByID(@PathVariable Long id) {
        Address address = addressService.findAddressById(id);
        return ResponseEntity.ok(address);
    }

    // POST http://localhost:8080/api/addresses

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address createdAddress = addressService.saveAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }
    
    // PUT http://localhost:8080/api/addresses/{id}

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        Address address = addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.ok(address);
    }

    // DELETE http://localhost:8080/api/addresses/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address with id: " + id + " has been deleted successfully");
    }
}
