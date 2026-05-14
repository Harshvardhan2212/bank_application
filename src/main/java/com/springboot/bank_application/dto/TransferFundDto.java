package com.springboot.bank_application.dto;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              Double amount) {
}
