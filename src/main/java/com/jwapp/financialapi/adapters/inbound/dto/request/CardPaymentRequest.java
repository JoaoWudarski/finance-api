package com.jwapp.financialapi.adapters.inbound.dto.request;

import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
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

    public CardTransaction toDomain(Long cardId) {
        return new CardTransaction(null, value, LocalDateTime.now(), description, Card.builder().id(cardId).build(), installments);
    }
}
