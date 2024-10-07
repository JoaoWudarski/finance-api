package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.payment.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Long> {

    List<CardPayment> findByCardAndDateTimeBetween(Card card, LocalDateTime initialDate, LocalDateTime finalDate);
}
