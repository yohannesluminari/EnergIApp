package it.epicode.energiapp.runners;

import com.github.javafaker.Faker;
import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.entities.enumEntities.Role;
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
            User user = new User();
            user.setEmail(faker.internet().emailAddress());
            user.setPassword("password123");
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setAvatar(faker.internet().avatar());
            user.setRole(Role.USER);

            userService.saveUser(user);
        }
    }
}
