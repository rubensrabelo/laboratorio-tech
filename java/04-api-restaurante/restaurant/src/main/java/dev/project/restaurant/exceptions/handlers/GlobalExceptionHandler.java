package dev.project.restaurant.exceptions.handlers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.project.restaurant.exceptions.domain.DataIntegrityViolationException;
import dev.project.restaurant.exceptions.domain.ResourceNotFoundException;
import dev.project.restaurant.exceptions.dtos.FieldErrorDTO;
import dev.project.restaurant.exceptions.dtos.StandardError;
import dev.project.restaurant.exceptions.dtos.ValidationError;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<StandardError> handleResourceNotFound(ResourceNotFoundException ex,
                        HttpServletRequest request) {
                HttpStatus status = HttpStatus.NOT_FOUND;
                StandardError error = new StandardError(
                                LocalDateTime.now(),
                                status.value(),
                                "Resource Not Found",
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                HttpStatus status = HttpStatus.BAD_REQUEST;

                List<FieldErrorDTO> fieldErrors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fe -> new FieldErrorDTO(fe.getField(), fe.getDefaultMessage()))
                                .toList();

                ValidationError error = new ValidationError(
                                LocalDateTime.now(),
                                status.value(),
                                "Validation Error",
                                "One or more fields are invalid",
                                request.getRequestURI(),
                                fieldErrors);
                return ResponseEntity.status(status).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<StandardError> handleGenericException(Exception ex, HttpServletRequest request) {
                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                StandardError error = new StandardError(
                                LocalDateTime.now(),
                                status.value(),
                                "Internal Server Error",
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(error);
        }


        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                HttpStatus status = HttpStatus.CONFLICT;
                StandardError error = new StandardError(
                                LocalDateTime.now(),
                                status.value(),
                                "Data Integrity Violation",
                                ex.getMessage(),
                                request.getRequestURI());
                return ResponseEntity.status(status).body(error);
        }
}
