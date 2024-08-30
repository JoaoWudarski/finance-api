package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Card;

public interface FindCard {

    Card byId(Long id);
}
