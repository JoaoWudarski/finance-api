package com.jwapp.financialapi.adapters.inbound.dto.request;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
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
