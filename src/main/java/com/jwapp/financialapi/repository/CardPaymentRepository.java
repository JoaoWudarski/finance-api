package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.payment.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Long> {
}
