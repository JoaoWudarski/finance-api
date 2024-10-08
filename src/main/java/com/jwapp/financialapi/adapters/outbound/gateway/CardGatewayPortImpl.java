package com.jwapp.financialapi.adapters.outbound.gateway;

import com.jwapp.financialapi.adapters.outbound.repository.CardRepository;
import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.ports.gateway.CardGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardGatewayPortImpl implements CardGatewayPort {

    private final CardRepository cardRepository;

    @Override
    public Card save(Card entity) {
        return cardRepository.save(entity);
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }
}
