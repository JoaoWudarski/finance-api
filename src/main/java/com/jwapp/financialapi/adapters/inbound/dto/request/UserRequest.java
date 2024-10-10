package com.jwapp.financialapi.adapters.inbound.dto.request;

import com.jwapp.financialapi.application.core.domain.User;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
         @NotEmpty String name
) {

    public User toDomain() {
        return User.builder()
                .name(name)
                .build();
    }
}
