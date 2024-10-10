package com.jwapp.financialapi.application.core.usecase.account;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.core.exception.ConflictException;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.CreateEntityPort;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.AccountGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountImpl implements CreateEntityPort<Account> {

    private final AccountGatewayPort accountGatewayPort;
    private final FindEntityPort<User, Long> findUserPort;

    @Override
    public Long createNew(Account account) {
        User user = account.getUser();
        if (!findUserPort.exists(user.getId()))
            throw new NotFoundException("User com id " + user.getId() + " nao existe!");

        if (!accountGatewayPort.findByUserAndBank(account.getUser(), account.getBank()).isEmpty())
            throw new ConflictException("Ja existe uma conta do " + account.getBank() + " para esse usuario!");

        return accountGatewayPort.save(account).getId();
    }
}
