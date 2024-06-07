package it.epicode.energiapp.runners;

import com.github.javafaker.Faker;
import it.epicode.energiapp.payloads.UserRegisterRequestPayloadDTO;
import it.epicode.energiapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            UserRegisterRequestPayloadDTO userDto = new UserRegisterRequestPayloadDTO(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    "password123"
            );

            try {
                userService.saveUser(userDto);
            } catch (Exception e) {
                // Gestisce eccezioni come l'email duplicata
                System.out.println("Failed to save user: " + e.getMessage());
            }
        }
    }
}
