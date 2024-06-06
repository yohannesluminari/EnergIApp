package it.epicode.energiapp.runners;

import com.github.javafaker.Faker;
import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.entities.enumEntities.ClientType;
import it.epicode.energiapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Order(3)
public class ClientsRunner implements CommandLineRunner {

    @Autowired
    private ClientService clientService;


    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        for (int j = 0; j < 5; j++){

            Client client = Client.builder()
                    .withBusinessName(faker.company().name())
                    .withVatNumber(faker.number().digits(11))
                    .withEmail(faker.internet().emailAddress())
                    .withDateAdded(LocalDate.now())
                    .withLastContactDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 365)))
                    .withAnnualRevenue(BigDecimal.valueOf(faker.number().randomDouble(2, 10000, 1000000)))
                    .withPec(faker.internet().emailAddress())
                    .withPhone(faker.phoneNumber().phoneNumber())
                    .withContactEmail(faker.internet().emailAddress())
                    .withContactFirstName(faker.name().firstName())
                    .withContactLastName(faker.name().lastName())
                    .withContactPhone(faker.phoneNumber().phoneNumber())
                    .withCompanyLogo(faker.internet().avatar())
                    .withCompanyName(faker.company().name())
                    .withClientType(ClientType.SPA)
                    .build();

            clientService.saveClient(client);
        }



    }
}
