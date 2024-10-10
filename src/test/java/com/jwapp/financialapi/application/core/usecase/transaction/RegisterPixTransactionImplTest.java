package com.jwapp.financialapi.application.core.usecase.transaction;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import com.jwapp.financialapi.application.ports.gateway.AccountTransactionGatewayPort;
import com.jwapp.financialapi.application.ports.gateway.BasicEntityGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterPixTransactionImplTest {

    @Mock
    private BasicEntityGatewayPort<Account> basicEntityGatewayPort;
    @Mock
    private AccountTransactionGatewayPort accountTransactionGatewayPort;

    private RegisterPixTransactionImpl registerPixTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerPixTransaction = new RegisterPixTransactionImpl(basicEntityGatewayPort, accountTransactionGatewayPort);
    }

    @Test
    @DisplayName("Dado um AccountTransaction com datetime, description e id da conta " +
            "Quando chamado o entrance " +
            "Então deve ser adicionado o saldo e ser salvo")
    void registerEntranceWithPixCase1() {
        AccountTransaction accountTransactionDB = new AccountTransaction();
        accountTransactionDB.setId(10L);
        Account account = new Account();
        account.setBalance(new BigDecimal("421.75"));

        when(basicEntityGatewayPort.findById(any())).thenReturn(Optional.of(account));
        when(accountTransactionGatewayPort.save(any(AccountTransaction.class))).thenReturn(accountTransactionDB);

        AccountTransaction accountTransaction = new AccountTransaction(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(10L));
        Long idAccount = registerPixTransaction.entrance(accountTransaction).getId();

        assertEquals(10L, idAccount);
        assertEquals(new BigDecimal("555.75"), account.getBalance());

        verify(basicEntityGatewayPort).findById(10L);
        verify(basicEntityGatewayPort).save(account);
        verify(accountTransactionGatewayPort).save(accountTransaction);
    }

    @Test
    @DisplayName("Dado um AccountTransaction com datetime, description e id da conta " +
            "Quando chamado o exit " +
            "Então deve ser removido o saldo e ser salvo")
    void registerPaymentWithPixCase1() {
        AccountTransaction accountTransactionDB = new AccountTransaction();
        accountTransactionDB.setId(10L);
        Account account = new Account();
        account.setBalance(new BigDecimal("202.11"));

        when(basicEntityGatewayPort.findById(any())).thenReturn(Optional.of(account));
        when(accountTransactionGatewayPort.save(any(AccountTransaction.class))).thenReturn(accountTransactionDB);

        AccountTransaction accountTransaction = new AccountTransaction(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(10L));
        Long idAccount = registerPixTransaction.exit(accountTransaction).getId();

        assertEquals(10L, idAccount);
        assertEquals(new BigDecimal("68.11"), account.getBalance());
        assertEquals(new BigDecimal("-134.0"), accountTransaction.getTransactionValue());

        verify(basicEntityGatewayPort).findById(10L);
        verify(basicEntityGatewayPort).save(account);
        verify(accountTransactionGatewayPort).save(accountTransaction);
    }
}