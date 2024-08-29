package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.ChangeBalanceAccount;
import com.jwapp.financialapi.usecase.FindAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ChangeBalanceAccountImpl implements ChangeBalanceAccount {

    private final AccountRepository accountRepository;
    private final FindAccount findAccount;

    @Override
    public void addValue(BigDecimal value, Long accountId) {
        Account account = findAccount.byId(accountId);
        account.addBalance(value);
        accountRepository.save(account);
    }

    @Override
    public void removeValue(BigDecimal value, Long accountId) {
        Account account = findAccount.byId(accountId);
        if (account.getBalance().subtract(value).compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Saldo insuficiente para completar a transacao!");

        account.removeBalance(value);
        accountRepository.save(account);
    }
}
