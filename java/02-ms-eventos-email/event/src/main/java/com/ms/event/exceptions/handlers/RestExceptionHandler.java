package com.ms.event.exceptions.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ms.event.exceptions.domain.EventFullException;
import com.ms.event.exceptions.domain.EventNotFoundException;
import com.ms.event.exceptions.dtos.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<StandardError> eventNotFoundHandler(EventNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Resource Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EventFullException.class)
    private ResponseEntity<StandardError> eventFullErrorHandler(EventFullException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Business Rule Violation",
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<StandardError> runtimeErrorHandler(RuntimeException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        StandardError error = new StandardError(
                Instant.now(),
                status.value(),
                "Internal Server Error",
                exception.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(status).body(error);
    }
}
