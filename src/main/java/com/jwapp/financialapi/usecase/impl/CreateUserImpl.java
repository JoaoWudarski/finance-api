package com.jwapp.financialapi.usecase.impl;

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
    public Long createNew(User user) {
        User userDb = userRepository.save(user);
        return userDb.getId();
    }
}
