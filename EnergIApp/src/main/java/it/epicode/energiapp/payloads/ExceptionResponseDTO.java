package it.epicode.energiapp.payloads;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt
) {
}
