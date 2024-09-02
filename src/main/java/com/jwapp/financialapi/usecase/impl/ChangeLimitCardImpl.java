package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.ChangeLimitCard;
import com.jwapp.financialapi.usecase.FindCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ChangeLimitCardImpl implements ChangeLimitCard {

    private final CardRepository cardRepository;
    private final FindCard findCard;

    @Override
    public void removeLimit(BigDecimal value, Long cardId) {
        Card card = findCard.byId(cardId);
        if (card.getUsedLimit().add(value).compareTo(card.getTotalLimit()) > 0)
            throw new IllegalArgumentException("Limite insuficiente para completar a transacao!");
        card.subtractLimit(value);
        cardRepository.save(card);
    }
}
