package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.controller.dto.request.CardRequest;

public interface CreateCard {

    Long createNew(CardRequest cardRequest);
}
