package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserAndBank(User user, String bank);
}
