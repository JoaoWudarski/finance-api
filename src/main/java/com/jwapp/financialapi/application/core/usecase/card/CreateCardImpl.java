package com.jwapp.financialapi.application.core.usecase.card;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.CreateEntityPort;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.CardGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCardImpl implements CreateEntityPort<Card> {

    private final CardGatewayPort cardGatewayPort;
    private final FindEntityPort<Account, Long> findAccountPort;

    @Override
    public Long createNew(Card card) {
        Account account = card.getAccount();
        if (!findAccountPort.exists(account.getId()))
            throw new NotFoundException("Nao existe uma conta com id " + account.getId() + " criada");

        return cardGatewayPort.save(card).getId();
    }
}
