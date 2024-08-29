package com.jwapp.financialapi.usecase;


import java.math.BigDecimal;

public interface ChangeBalanceAccount {

    void addValue(BigDecimal value, Long accountId);
}
