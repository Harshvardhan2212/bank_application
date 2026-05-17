package com.springboot.bank_application.service.impl;

import com.springboot.bank_application.dto.AccountDto;
import com.springboot.bank_application.dto.TransactionDto;
import com.springboot.bank_application.dto.TransferFundDto;
import com.springboot.bank_application.entity.Account;
import com.springboot.bank_application.entity.Transaction;
import com.springboot.bank_application.exception.LowBalanceException;
import com.springboot.bank_application.exception.ResourceNotFoundException;
import com.springboot.bank_application.mapper.AccountMapper;
import com.springboot.bank_application.mapper.TransactionMapper;
import com.springboot.bank_application.repository.AccountRepository;
import com.springboot.bank_application.repository.TransactionRepository;
import com.springboot.bank_application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";

    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";

    private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,TransactionRepository transactionRepository,TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto findById(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDto> findAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountMapper::toDto).toList();
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setAccountHolderName(accountDto.accountHolderName());
        account.setBalance(accountDto.balance());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    public void deleteAccount(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new LowBalanceException("insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);


        Transaction transaction = new Transaction();
        transaction.setAccountId(savedAccount.getId());
        transaction.setAmount(amount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transactionRepository.save(transaction);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto deposit(Long id, Double deposit) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        account.setBalance(account.getBalance() + deposit);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(savedAccount.getId());
        transaction.setAmount(deposit);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transactionRepository.save(transaction);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public void transferFund(TransferFundDto transferFundDto) {
        Account fromAccount = accountRepository.findById(transferFundDto.fromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Account toAccount = accountRepository.findById(transferFundDto.toAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (transferFundDto.amount().compareTo(fromAccount.getBalance()) > 0) {
            throw new LowBalanceException("insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.amount());
        toAccount.setBalance(toAccount.getBalance() + transferFundDto.amount());
        accountRepository.save(toAccount);
        accountRepository.save(fromAccount);


        Transaction transaction = new Transaction();
        transaction.setAccountId(transferFundDto.toAccountId());
        transaction.setAmount(transferFundDto.amount());
        transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
        transactionRepository.save(transaction);
    }

    public List<TransactionDto> getTransactionById(Long accountId) {
         List<Transaction> transactions =  transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
        return transactions.stream().map(transactionMapper::toDto).toList();
    }
}
