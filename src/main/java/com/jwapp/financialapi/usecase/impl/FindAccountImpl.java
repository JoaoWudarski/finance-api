package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.FindAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountImpl implements FindAccount {

    private final AccountRepository accountRepository;

    @Override
    public boolean exists(Long id) {
        return accountRepository.existsById(id);
    }
}
