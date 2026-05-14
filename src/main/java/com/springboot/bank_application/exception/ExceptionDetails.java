package com.springboot.bank_application.exception;

import java.time.LocalDateTime;

public record ExceptionDetails(LocalDateTime timestamp,
                               String message,
                               String details,
                               String errorCode) {
}
