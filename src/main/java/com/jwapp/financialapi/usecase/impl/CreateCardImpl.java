package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.controller.dto.request.CardRequest;
import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.CreateCard;
import com.jwapp.financialapi.usecase.FindAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCardImpl implements CreateCard {

    private final CardRepository cardRepository;
    private final FindAccount findAccount;

    @Override
    public Long createNew(CardRequest cardRequest) {
        Card card = cardRequest.toDomain();

        if (!findAccount.exists(cardRequest.accountId()))
            throw new NotFoundException("Nao existe uma conta com id " + cardRequest.accountId() + " criada");

        return cardRepository.save(card).getId();
    }
}
