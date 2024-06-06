package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.exceptions.BadRequestException;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.payloads.UserRegisterRequestPayloadDTO;
import it.epicode.energiapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);

        User user = userOptional.orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));

        return ResponseEntity.ok(user);
    }


    @PutMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody @Validated UserRegisterRequestPayloadDTO userDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Se ci sono errori di validazione, lancia una BadRequestException
            String errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .reduce("", (s, s2) -> s + s2);
            throw new BadRequestException(errorMessages);
        }

        // Aggiorna l'utente e restituisci lo stato HTTP 200 OK con l'utente aggiornato
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
