package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.repository.AccountPaymentRepository;
import com.jwapp.financialapi.usecase.ChangeBalanceAccount;
import com.jwapp.financialapi.usecase.RegisterPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterPaymentImpl implements RegisterPayment {

    private final AccountPaymentRepository accountPaymentRepository;
    private final ChangeBalanceAccount changeBalanceAccount;

    @Override
    public Long withPix(AccountPayment accountPayment) {
        changeBalanceAccount.removeValue(accountPayment.getTransactionValue(), accountPayment.getAccount().getId());
        return accountPaymentRepository.save(accountPayment).getId();
    }
}
