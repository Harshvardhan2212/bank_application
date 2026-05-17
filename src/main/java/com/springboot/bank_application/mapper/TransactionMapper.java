package com.springboot.bank_application.mapper;

import com.springboot.bank_application.dto.TransactionDto;
import com.springboot.bank_application.entity.Transaction;

public class TransactionMapper {
    public Transaction toEntity(TransactionDto transactionDto){
        return new Transaction(
                transactionDto.id(),
                transactionDto.accountId(),
                transactionDto.amount(),
                transactionDto.transactionType(),
                transactionDto.timestamp()
        );
    }

    public TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTimestamp()
        );
    }
}
