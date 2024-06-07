
package it.epicode.energiapp.controllers;


import it.epicode.energiapp.exceptions.BadRequestException;
import it.epicode.energiapp.payloads.UserLoginRequestDTO;
import it.epicode.energiapp.payloads.UserLoginResponseDTO;
import it.epicode.energiapp.payloads.UserRegisterRequestPayloadDTO;
import it.epicode.energiapp.services.AuthService;
import it.epicode.energiapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // POST http://localhost:8080/api/auth/login

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO loginPayload) {
        String token = authService.authenticateUserAndGenerateToken(loginPayload);
        UserLoginResponseDTO response = new UserLoginResponseDTO(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST http://localhost:8080/api/auth/register

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Validated UserRegisterRequestPayloadDTO registerPayload,
            BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        String result = userService.saveUser(registerPayload);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}