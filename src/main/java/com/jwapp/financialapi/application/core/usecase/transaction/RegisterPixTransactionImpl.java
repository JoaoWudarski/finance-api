package com.jwapp.financialapi.application.core.usecase.transaction;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import com.jwapp.financialapi.application.ports.gateway.AccountTransactionGatewayPort;
import com.jwapp.financialapi.application.ports.gateway.BasicEntityGatewayPort;
import org.springframework.stereotype.Component;

@Component
public class RegisterPixTransactionImpl extends RegisterTransactionAbstract<AccountTransaction, Account> {

    private final AccountTransactionGatewayPort accountTransactionGatewayPort;

    public RegisterPixTransactionImpl(BasicEntityGatewayPort<Account> channelTransactionGatewayPort, AccountTransactionGatewayPort accountTransactionGatewayPort) {
        super(channelTransactionGatewayPort);
        this.accountTransactionGatewayPort = accountTransactionGatewayPort;
    }

    @Override
    public AccountTransaction entrance(AccountTransaction transaction) {
        super.add(transaction.getTransactionValue(), transaction.getAccount().getId());
        return accountTransactionGatewayPort.save(transaction);
    }

    @Override
    public AccountTransaction exit(AccountTransaction transaction) {
        super.remove(transaction.getTransactionValue(), transaction.getAccount().getId());
        return accountTransactionGatewayPort.save(transaction);
    }
}
