package it.epicode.energiapp.payloads;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        String message,
        HttpStatus httpStatus,
        LocalDateTime createdAt

) {
    public ExceptionResponseDTO(String message, HttpStatus httpStatus, LocalDateTime createdAt) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.createdAt = createdAt;
    }
}
