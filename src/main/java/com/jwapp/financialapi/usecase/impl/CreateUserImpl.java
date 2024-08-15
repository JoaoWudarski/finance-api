package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.controller.dto.request.UserRequest;
import com.jwapp.financialapi.domain.User;
import com.jwapp.financialapi.repository.UserRepository;
import com.jwapp.financialapi.usecase.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserImpl implements CreateUser {

    private final UserRepository userRepository;

    @Override
    public Long createNew(UserRequest userRequest) {
        User userDb = userRepository.save(userRequest.toDomain());
        return userDb.getId();
    }
}
