package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.Account;
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
    public Long createNew(Card card) {
        Account account = card.getAccount();
        if (!findAccount.exists(account.getId()))
            throw new NotFoundException("Nao existe uma conta com id " + account.getId() + " criada");

        return cardRepository.save(card).getId();
    }
}
