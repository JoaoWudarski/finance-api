package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.FindCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindCardImpl implements FindCard {

    private final CardRepository cardRepository;

    @Override
    public Card byId(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cartao com id " + id + " nao foi encontrado!"));
    }
}
