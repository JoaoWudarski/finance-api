package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.controller.dto.request.AccountRequest;
import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.exception.ConflictException;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.CreateAccount;
import com.jwapp.financialapi.usecase.FindUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountImpl implements CreateAccount {

    private final AccountRepository accountRepository;
    private final FindUser findUser;

    @Override
    public Long createNew(AccountRequest accountRequest) {
        Account account = accountRequest.toDomain();

        if (!findUser.exists(accountRequest.userId()))
            throw new NotFoundException("User com id " + accountRequest.userId() + " não existe!");

        if (!accountRepository.findByUserAndBank(account.getUser(), account.getBank()).isEmpty())
            throw new ConflictException("Já existe uma conta do " + account.getBank() + " para esse usuario!");

        return accountRepository.save(account).getId();
    }
}
