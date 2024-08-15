package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.controller.dto.request.UserRequest;

public interface CreateUser {

    Long createNew(UserRequest userRequest);
}
