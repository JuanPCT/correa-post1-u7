package co.edu.ufps.biblioteca.controller;

import co.edu.ufps.biblioteca.domain.PagoRechazadoException;
import co.edu.ufps.biblioteca.model.LimiteMultasPendientesException;
import co.edu.ufps.biblioteca.model.MultaNotFoundException;
import co.edu.ufps.biblioteca.model.MultaYaPagadaException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MultaNotFoundException.class)
    ResponseEntity<Map<String, String>> notFound(RuntimeException ex) { return error(HttpStatus.NOT_FOUND, ex.getMessage()); }

    @ExceptionHandler({LimiteMultasPendientesException.class, MultaYaPagadaException.class})
    ResponseEntity<Map<String, String>> conflict(RuntimeException ex) { return error(HttpStatus.CONFLICT, ex.getMessage()); }

    @ExceptionHandler(PagoRechazadoException.class)
    ResponseEntity<Map<String, String>> paymentFailed(RuntimeException ex) { return error(HttpStatus.PAYMENT_REQUIRED, ex.getMessage()); }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, String>> badRequest(Exception ex) {
        return error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> validation(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().findFirst()
            .map(e -> e.getField() + ": " + e.getDefaultMessage()).orElse("Solicitud inválida");
        return error(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("error", message == null ? status.getReasonPhrase() : message));
    }
}
