package com.jwapp.financialapi.usecase;


import java.math.BigDecimal;

public interface ChangeLimitCard {

    void removeLimit(BigDecimal value, Long cardId);
}
