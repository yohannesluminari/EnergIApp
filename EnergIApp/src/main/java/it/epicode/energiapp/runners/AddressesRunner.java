package it.epicode.energiapp.runners;

import com.github.javafaker.Faker;
import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.services.AddressService;
import it.epicode.energiapp.services.ClientService;
import it.epicode.energiapp.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Order(4)
public class AddressesRunner implements CommandLineRunner {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public void run(String... args) throws Exception {
        // Fetch all clients
        List<Client> clients = clientService.findAllClients(PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        // Fetch all municipalities
        List<Municipality> municipalities = municipalityService.getAllMunicipalities(PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        // Random generator
        Random random = new Random();
        Faker faker = new Faker();

        for (Client client : clients) {
            // Select a random municipality
            Municipality municipality = municipalities.get(random.nextInt(municipalities.size()));

            // Create a new address
            Address address = Address.builder()
                    .withStreet(faker.address().streetName())
                    .withStreetNumber(faker.address().buildingNumber())
                    .withLocality(faker.address().city())
                    .withZipCode(faker.address().zipCode())
                    .withMunicipality(municipality)
                    .withClient(client)
                    .build();
            // Save the address
            addressService.saveAddress(address);
        }
    }
}
