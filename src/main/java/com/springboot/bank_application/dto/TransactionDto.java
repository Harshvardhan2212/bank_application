package com.springboot.bank_application.dto;

import java.sql.Timestamp;

public record TransactionDto(
        Long id,
        Long accountId,
        Double amount,
        String transactionType,
       Timestamp timestamp
        ) {

}
