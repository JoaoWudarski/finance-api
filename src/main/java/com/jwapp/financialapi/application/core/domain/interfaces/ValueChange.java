package com.jwapp.financialapi.application.core.domain.interfaces;

import java.math.BigDecimal;

public interface ValueChange {

    boolean addValue(BigDecimal value);
    boolean removeValue(BigDecimal value);
    BigDecimal getValue();
}
