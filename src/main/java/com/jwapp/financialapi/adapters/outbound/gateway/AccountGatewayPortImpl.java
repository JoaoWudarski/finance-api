package com.jwapp.financialapi.adapters.outbound.gateway;

import com.jwapp.financialapi.adapters.outbound.repository.AccountRepository;
import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.ports.gateway.AccountGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountGatewayPortImpl implements AccountGatewayPort {

    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findByUserAndBank(User user, String bank) {
        return accountRepository.findByUserAndBank(user, bank);
    }

    @Override
    public Boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
}
