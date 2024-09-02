package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.receipt.AccountReceipt;

public interface RegisterEntrance {

    Long withPix(AccountReceipt accountReceipt);
}
