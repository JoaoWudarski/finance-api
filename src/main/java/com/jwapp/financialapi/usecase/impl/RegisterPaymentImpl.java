package com.jwapp.financialapi.usecase.impl;

import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.domain.payment.CardPayment;
import com.jwapp.financialapi.repository.AccountPaymentRepository;
import com.jwapp.financialapi.repository.CardPaymentRepository;
import com.jwapp.financialapi.usecase.ChangeBalanceAccount;
import com.jwapp.financialapi.usecase.ChangeLimitCard;
import com.jwapp.financialapi.usecase.RegisterPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RegisterPaymentImpl implements RegisterPayment {

    private final AccountPaymentRepository accountPaymentRepository;
    private final CardPaymentRepository cardPaymentRepository;
    private final ChangeBalanceAccount changeBalanceAccount;
    private final ChangeLimitCard changeLimitCard;

    @Override
    public Long withPix(AccountPayment accountPayment) {
        changeBalanceAccount.removeValue(accountPayment.getTransactionValue(), accountPayment.getAccount().getId());
        return accountPaymentRepository.save(accountPayment).getId();
    }

    @Override
    public void withCreditCard(CardPayment cardPayment) {
        changeLimitCard.removeLimit(cardPayment.getTransactionValue(), cardPayment.getCard().getId());
        LocalDateTime transactionDate = cardPayment.getDateTime();
        String installmentDescription = cardPayment.getDescription() + " (%d/%d)";

        cardPayment.setTransactionValue(cardPayment.createInstallmentValue());
        for (int install = 1; install <= cardPayment.getInstallments(); install++) {
            cardPayment.setId(null);
            cardPayment.setDateTime(transactionDate.plusMonths(install - 1L));
            cardPayment.setDescription(String.format(installmentDescription, install, cardPayment.getInstallments()));
            cardPaymentRepository.save(new CardPayment(cardPayment));
        }
    }
}
