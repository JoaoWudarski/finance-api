package com.jwapp.financialapi.adapters.outbound.gateway;

import com.jwapp.financialapi.adapters.outbound.repository.AccountTransactionRepository;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import com.jwapp.financialapi.application.ports.gateway.AccountTransactionGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountTransactionGatewayPortImpl implements AccountTransactionGatewayPort {

    private final AccountTransactionRepository accountTransactionRepository;

    @Override
    public AccountTransaction save(AccountTransaction entity) {
        return accountTransactionRepository.save(entity);
    }

    @Override
    public Optional<AccountTransaction> findById(Long id) {
        return accountTransactionRepository.findById(id);
    }
}
