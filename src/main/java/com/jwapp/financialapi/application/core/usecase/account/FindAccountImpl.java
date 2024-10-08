package com.jwapp.financialapi.application.core.usecase.account;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.AccountGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountImpl implements FindEntityPort<Account, Long> {

    private final AccountGatewayPort accountGatewayPort;

    @Override
    public boolean exists(Long id) {
        return accountGatewayPort.existsById(id);
    }

    @Override
    public Account byId(Long id) {
        return accountGatewayPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Nao existe conta com id " + id));
    }
}

