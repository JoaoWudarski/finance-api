package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.payment.AccountPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountPaymentRepository extends JpaRepository<AccountPayment, Long> {
}
