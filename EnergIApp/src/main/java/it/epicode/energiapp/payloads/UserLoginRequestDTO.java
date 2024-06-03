package it.epicode.energiapp.payloads;

public record UserLoginRequestDTO(
        String email,
        String password
) {
}
