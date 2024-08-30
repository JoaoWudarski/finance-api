package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.domain.payment.CardPayment;

public interface RegisterPayment {

    Long withPix(AccountPayment accountPayment);
    void withCreditCard(CardPayment cardPayment);
}
