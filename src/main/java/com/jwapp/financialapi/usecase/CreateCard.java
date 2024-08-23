package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Card;

public interface CreateCard {

    Long createNew(Card card);
}
