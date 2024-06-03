package it.epicode.energiapp.runners;

import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.entities.enumEntities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class EnergIAppRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {


        // Esempio di istanza per User
        User user = new User("example@example.com", "password", "John", "Doe");
        user.setAvatar("avatar.png");
        user.setRole(Role.USER);

        // Esempio di istanza per Client
        Client client = new Client();
        client.setBusinessName("Example Company");
        client.setVatNumber("123456789");
        client.setEmail("info@example.com");
        client.setDateAdded(LocalDate.now());
        client.setLastContactDate(LocalDate.of(2023, 5, 15));
        client.setAnnualRevenue(new BigDecimal("1000000"));
        client.setPec("pec@example.com");
        client.setPhone("1234567890");
        client.setContactEmail("contact@example.com");
        client.setContactFirstName("Jane");
        client.setContactLastName("Doe");
        client.setContactPhone("9876543210");
        client.setCompanyLogo("logo.png");

    }
}

