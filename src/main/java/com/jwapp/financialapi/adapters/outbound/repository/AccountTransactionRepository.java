package com.jwapp.financialapi.adapters.outbound.repository;

import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
