package it.epicode.energiapp.services;


import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.exceptions.UnauthorizedException;
import it.epicode.energiapp.payloads.UserLoginRequestDTO;
import it.epicode.energiapp.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtils jwtTools;

    @Autowired
    PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginRequestDTO loginPayload) {
        User user = userService.findByEmail(loginPayload.email());
        if (bcrypt.matches(loginPayload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials! Try login again.");
        }

    }
}


