package com.springboot.bank_application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFound(Exception exception, WebRequest request) {
        ExceptionDetails errorDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "ACCOUNT_NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LowBalanceException.class)
    public ResponseEntity<ExceptionDetails> handleLowBalance(Exception exception, WebRequest request) {
        ExceptionDetails errorDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "LOW_BALANCE"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleGenericException(Exception exception, WebRequest request) {

        ExceptionDetails errorDetails = new ExceptionDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
