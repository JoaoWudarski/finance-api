package com.jwapp.financialapi.controller.dto.request;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.payment.AccountPayment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequest(
        @NotBlank String description,
        @NotNull @Min(value = 1) BigDecimal value
) {

    public AccountPayment toDomain(Long accountId) {
        return new AccountPayment(null, value, LocalDateTime.now(), description, Account.builder().id(accountId).build());
    }
}
