package com.jwapp.financialapi.application.ports;

import java.math.BigDecimal;

public interface ChangeCurrencyValuePort<C> {

    void add(BigDecimal value, C id);
    void remove(BigDecimal value, C id);
}
