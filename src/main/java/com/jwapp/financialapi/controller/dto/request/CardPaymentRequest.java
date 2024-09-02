package com.jwapp.financialapi.controller.dto.request;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.payment.CardPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CardPaymentRequest(
        @NotNull @Min(value = 1) Integer installments,
        @NotBlank String description,
        @NotNull @Min(value = 1) BigDecimal value
) {

    public CardPayment toDomain(Long cardId) {
        return new CardPayment(null, value, LocalDateTime.now(), description, Card.builder().id(cardId).build(), installments);
    }
}
