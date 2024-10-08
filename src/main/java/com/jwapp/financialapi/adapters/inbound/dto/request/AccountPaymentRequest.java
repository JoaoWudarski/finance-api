package com.jwapp.financialapi.adapters.inbound.dto.request;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountPaymentRequest(
        @NotBlank String description,
        @NotNull @Min(value = 1) BigDecimal value
) {

    public AccountTransaction toDomain(Long accountId) {
        return new AccountTransaction(null, value, LocalDateTime.now(), description, Account.builder().id(accountId).build());
    }
}
