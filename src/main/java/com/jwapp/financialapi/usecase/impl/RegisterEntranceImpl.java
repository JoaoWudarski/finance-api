package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.receipt.AccountReceipt;
import com.jwapp.financialapi.repository.AccountReceiptRepository;
import com.jwapp.financialapi.usecase.ChangeBalanceAccount;
import com.jwapp.financialapi.usecase.RegisterEntrance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterEntranceImpl implements RegisterEntrance {

    private final AccountReceiptRepository accountReceiptRepository;
    private final ChangeBalanceAccount changeBalanceAccount;

    @Override
    public Long withPix(AccountReceipt accountReceipt) {
        changeBalanceAccount.addValue(accountReceipt.getTransactionValue(), accountReceipt.getAccount().getId());
        accountReceipt = accountReceiptRepository.save(accountReceipt);
        return accountReceipt.getId();
    }
}
