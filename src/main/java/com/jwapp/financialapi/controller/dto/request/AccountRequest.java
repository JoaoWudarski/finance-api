package com.jwapp.financialapi.controller.dto.request;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountRequest(
        @NotEmpty String bank,
        @NotNull Long userId
) {

    public Account toDomain() {
        return Account.builder()
                .user(new User(userId))
                .bank(bank)
                .balance(BigDecimal.ZERO)
                .build();
    }
}
