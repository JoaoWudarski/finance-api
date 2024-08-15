package com.jwapp.financialapi.controller.dto.request;

import com.jwapp.financialapi.domain.User;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
         @NotEmpty String name
) {

    public User toDomain() {
        return new User(null, name, null, null);
    }
}
