package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // GET all
    public Page<Address> findAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    // GET id
    public Address findAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found with id: " + id));
    }

    // POST
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }


    // PUT
    public Address updateAddress(Long id, Address updatedAddress) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            throw new NotFoundException("Address not found with id: " + id);
        }
        Address address = optionalAddress.get();
        address.setStreet(updatedAddress.getStreet());
        address.setStreetNumber(updatedAddress.getStreetNumber());
        address.setLocality(updatedAddress.getLocality());
        address.setZipCode(updatedAddress.getZipCode());
        address.setMunicipality(updatedAddress.getMunicipality());
        address.setClient(updatedAddress.getClient());
        return addressRepository.save(address);
    }

    // DELETE
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new NotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}
