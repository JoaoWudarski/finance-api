package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.usecase.model.Bill;

public interface FindBill {

    Bill byCardIdAndMonths(Long cardId, Long minusMonths);
}
