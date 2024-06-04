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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressesRunner implements CommandLineRunner {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();
        Pageable pageable = PageRequest.of(0, 10); // 10 elementi per pagina

        Page<Client> clientPage = clientService.findAllClients(pageable);
        Page<Municipality> municipalityPage = municipalityService.getAllMunicipalities(pageable);

        List<Client> clients = clientPage.getContent();
        List<Municipality> municipalities = municipalityPage.getContent();

        // Controlla se ci sono elementi nelle liste
        if (clients.isEmpty() || municipalities.isEmpty()) {
            System.out.println("No enough data to create addresses");
            return;
        }

        for (int j = 0; j < 5; j++) {

            Address address = Address.builder()
                    .withStreet(faker.address().streetName())
                    .withStreetNumber(faker.address().streetAddressNumber())
                    .withLocality(faker.address().city())
                    .withZipCode(faker.address().zipCode())
                    .withClient(clients.get(faker.number().numberBetween(0, clientPage.getNumberOfElements())))
                    .withMunicipality(municipalities.get(faker.number().numberBetween(0, municipalityPage.getNumberOfElements())))
                    .build();

            addressService.saveAddress(address);
        }
    }
}
