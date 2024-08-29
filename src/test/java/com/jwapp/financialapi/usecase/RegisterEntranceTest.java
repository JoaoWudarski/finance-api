package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.receipt.AccountReceipt;
import com.jwapp.financialapi.repository.AccountReceiptRepository;
import com.jwapp.financialapi.usecase.impl.RegisterEntranceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterEntranceTest {

    @Mock
    private AccountReceiptRepository accountReceiptRepository;
    @Mock
    private ChangeBalanceAccount changeBalanceAccount;

    private RegisterEntrance registerEntrance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerEntrance = new RegisterEntranceImpl(accountReceiptRepository, changeBalanceAccount);
    }

    @Test
    @DisplayName("Dado um AccountReceipt com datetime, description e id da conta " +
                 "Quando chamado o withPix " +
                 "Então deve ser adicionado o saldo e salvo")
    void registerEntranceWithPixCase1() {
        AccountReceipt accountReceiptBD = new AccountReceipt();
        accountReceiptBD.setId(10L);
        when(accountReceiptRepository.save(any(AccountReceipt.class))).thenReturn(accountReceiptBD);

        AccountReceipt accountReceipt = new AccountReceipt(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(1L));
        Long idAccount = registerEntrance.withPix(accountReceipt);

        assertEquals(10L, idAccount);

        verify(changeBalanceAccount).addValue(new BigDecimal("134.0"), 1L);
        verify(accountReceiptRepository).save(accountReceipt);
    }

    @Test
    @DisplayName("Dado um AccountReceipt com datetime, description e id da conta " +
                 "Quando chamado o withPix e der erro ao adicionar o valor " +
                 "Então o erro deve ser repassado")
    void registerEntranceWithPixCase2() {
        doThrow(IllegalArgumentException.class).when(changeBalanceAccount).addValue(any(), any());

        AccountReceipt accountReceipt = new AccountReceipt(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(1L));
        assertThrows(IllegalArgumentException.class, () -> registerEntrance.withPix(accountReceipt));

        verify(changeBalanceAccount).addValue(new BigDecimal("134.0"), 1L);
        verify(accountReceiptRepository, never()).save(any());
    }
}