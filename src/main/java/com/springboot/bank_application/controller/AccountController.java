package com.springboot.bank_application.controller;

import com.springboot.bank_application.dto.AccountDto;
import com.springboot.bank_application.dto.TransactionDto;
import com.springboot.bank_application.dto.TransferFundDto;
import com.springboot.bank_application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> findAll() {
        List<AccountDto> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id) {
        AccountDto accountDto = accountService.findById(id);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        AccountDto savedAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        AccountDto savedAccount = accountService.updateAccount(id, accountDto);
        return ResponseEntity.ok(savedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(Map.of("message", "Account deleted"));
    }

    @PutMapping("/{id}/deposite")
    public ResponseEntity<AccountDto> depositeAccount(@PathVariable Long id,
                                                      @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto updatedAccount = accountService.deposit(id, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawAccount(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        AccountDto account = accountService.withdraw(id, request.get("amount"));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transferFund(@RequestBody TransferFundDto transferFundDto) {
        accountService.transferFund(transferFundDto);
        return ResponseEntity.ok(Map.of("message", "Transfer successful"));
    }

    @GetMapping("/{accountId}/transaction")
    public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@PathVariable Long accountId) {
        List<TransactionDto> transactions = accountService.getTransactionById(accountId);
        return ResponseEntity.ok(transactions);
    }
}
