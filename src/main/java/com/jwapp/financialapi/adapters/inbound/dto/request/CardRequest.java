package com.jwapp.financialapi.adapters.inbound.dto.request;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.Card;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CardRequest(
        @NotEmpty String flag,
        @NotNull @Min(1) @Max(31) Integer closeDay,
        @NotNull @Min(1) @Max(31) Integer paymentDay,
        @NotNull @Min(1) BigDecimal totalLimit,
        @NotNull Long accountId
) {

    public Card toDomain() {
        return Card.builder()
                .account(new Account(accountId))
                .flagCard(flag)
                .closeDay(closeDay)
                .paymentDay(paymentDay)
                .totalLimit(totalLimit)
                .usedLimit(BigDecimal.ZERO)
                .build();
    }
}
