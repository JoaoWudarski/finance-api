package com.jwapp.financialapi.controller.dto.request;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.receipt.AccountReceipt;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReceiveRequest(
        String description,
        BigDecimal value
) {

    public AccountReceipt toDomain(Long accountId) {
        return new AccountReceipt(null, value, LocalDateTime.now(), description, Account.builder().id(accountId).build());
    }
}
