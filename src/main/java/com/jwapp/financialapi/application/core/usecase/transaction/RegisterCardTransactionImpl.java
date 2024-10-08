package com.jwapp.financialapi.application.core.usecase.transaction;

import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import com.jwapp.financialapi.application.ports.gateway.CardTransactionGatewayPort;
import com.jwapp.financialapi.application.ports.gateway.BasicEntityGatewayPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterCardTransactionImpl extends RegisterTransactionAbstract<CardTransaction, Card> {

    private final CardTransactionGatewayPort cardTransactionGatewayPort;

    public RegisterCardTransactionImpl(BasicEntityGatewayPort<Card> channelTransactionGatewayPort, CardTransactionGatewayPort cardTransactionGatewayPort) {
        super(channelTransactionGatewayPort);
        this.cardTransactionGatewayPort = cardTransactionGatewayPort;
    }

    @Override
    public CardTransaction entrance(CardTransaction transaction) {
        return null;
    }

    @Override
    public CardTransaction exit(CardTransaction transaction) {
        removeLimit(transaction);
        LocalDateTime transactionDate = transaction.getDateTime();
        String installmentDescription = transaction.getDescription() + " (%d/%d)";

        transaction.setTransactionValue(super.negateValue(transaction.createInstallmentValue()));
        for (int install = 1; install <= transaction.getInstallments(); install++) {
            transaction.setId(null);
            transaction.setDateTime(transactionDate.plusMonths(install - 1L));
            transaction.setDescription(String.format(installmentDescription, install, transaction.getInstallments()));
            cardTransactionGatewayPort.save(new CardTransaction(transaction));
        }
        return transaction;
    }

    private void removeLimit(CardTransaction transaction) {
        if (!super.remove(transaction.getTransactionValue(), transaction.getCard().getId()))
            throw new IllegalArgumentException("Limite insuficiente para completar a transacao!");
    }
}
