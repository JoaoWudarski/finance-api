package com.jwapp.financialapi.adapters.outbound.repository;

import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransactionRepository extends JpaRepository<CardTransaction, Long> {
}
