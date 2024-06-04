
package it.epicode.energiapp.controllers;

import it.epicode.energiapp.exceptions.BadRequestException;
import it.epicode.energiapp.payloads.UserLoginRequestDTO;
import it.epicode.energiapp.payloads.UserLoginResponseDTO;
import it.epicode.energiapp.payloads.UserRegisterRequestPayloadDTO;
import it.epicode.energiapp.payloads.UserRegisterResponsePayloadDTO;
import it.epicode.energiapp.services.AuthService;
import it.epicode.energiapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // POST login
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO loginPayload) {
        String token = authService.authenticateUserAndGenerateToken(loginPayload);
        UserLoginResponseDTO response = new UserLoginResponseDTO(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST register

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponsePayloadDTO> register(@RequestBody @Validated UserRegisterRequestPayloadDTO registerPayload, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        UserRegisterResponsePayloadDTO response = userService.saveUser(registerPayload);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
