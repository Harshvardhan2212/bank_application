package com.springboot.bank_application.repository;

import com.springboot.bank_application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
