package com.jwapp.financialapi.adapters.outbound.repository;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserAndBank(User user, String bank);
}
