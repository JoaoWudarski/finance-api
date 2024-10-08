package com.jwapp.financialapi.application.core.usecase.card;

import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.CardGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCardImpl implements FindEntityPort<Card, Long> {

    private final CardGatewayPort cardGatewayPort;

    @Override
    public Card byId(Long id) {
        return cardGatewayPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Cartao com id " + id + " nao foi encontrado!"));
    }

    @Override
    public boolean exists(Long id) {
        return cardGatewayPort.findById(id).isPresent();
    }
}
