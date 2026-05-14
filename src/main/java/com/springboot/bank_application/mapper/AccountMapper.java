package com.springboot.bank_application.mapper;

import com.springboot.bank_application.dto.AccountDto;
import com.springboot.bank_application.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }

    public Account toEntity(AccountDto accountDto) {
        return new Account(
                accountDto.id(),
                accountDto.accountHolderName(),
                accountDto.balance()
        );
    }
}
