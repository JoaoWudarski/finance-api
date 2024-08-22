package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.repository.UserRepository;
import com.jwapp.financialapi.usecase.FindUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserImpl implements FindUser {

    private final UserRepository userRepository;

    @Override
    public boolean exists(Long id) {
        return userRepository.findById(id).isPresent();
    }
}
