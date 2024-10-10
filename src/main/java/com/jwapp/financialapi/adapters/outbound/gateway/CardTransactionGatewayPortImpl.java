package com.jwapp.financialapi.adapters.outbound.gateway;

import com.jwapp.financialapi.adapters.outbound.repository.CardTransactionRepository;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import com.jwapp.financialapi.application.ports.gateway.CardTransactionGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardTransactionGatewayPortImpl implements CardTransactionGatewayPort {

    private final CardTransactionRepository cardTransactionRepository;

    @Override
    public CardTransaction save(CardTransaction entity) {
        return cardTransactionRepository.save(entity);
    }

    @Override
    public Optional<CardTransaction> findById(Long id) {
        return cardTransactionRepository.findById(id);
    }
}
