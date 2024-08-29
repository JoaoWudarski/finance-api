package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.payment.AccountPayment;

public interface RegisterPayment {

    Long withPix(AccountPayment accountPayment);
}
