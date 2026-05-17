package com.springboot.bank_application.service;

import com.springboot.bank_application.dto.AccountDto;
import com.springboot.bank_application.dto.TransactionDto;
import com.springboot.bank_application.dto.TransferFundDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    List<AccountDto> findAll();

    AccountDto findById(Long id);

    AccountDto updateAccount(Long id, AccountDto accountDto);

    void deleteAccount(Long id);

    AccountDto deposit(Long id, Double deposit);

    AccountDto withdraw(Long id, Double amount);

    //    AccountDto
    void transferFund(TransferFundDto transferFundDto);

    List<TransactionDto> getTransactionById(Long accountId);
}
