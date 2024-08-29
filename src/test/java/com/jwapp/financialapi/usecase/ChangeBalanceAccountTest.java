package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.impl.ChangeBalanceAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChangeBalanceAccountTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private FindAccount findAccount;

    private ChangeBalanceAccount changeBalanceAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        changeBalanceAccount = new ChangeBalanceAccountImpl(accountRepository, findAccount);
    }

    @Test
    @DisplayName("Dado um valor e um id da conta " +
                 "Quando chamado o addValue " +
                 "Entao deve ser adicionado o valor ao balance atual e salvo na base")
    void addValueCase1() {
        Account account = new Account();
        account.setBalance(new BigDecimal("123.43"));

        when(findAccount.byId(any())).thenReturn(account);

        changeBalanceAccount.addValue(new BigDecimal("432.32"), 10L);

        assertEquals(new BigDecimal("555.75"), account.getBalance());

        verify(findAccount).byId(10L);
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Dado um valor e um id da conta " +
                 "Quando chamado o removeValue e o usuario tiver saldo " +
                 "Entao deve ser removido o valor ao balance atual e salvo na base")
    void removeValueCase1() {
        Account account = new Account();
        account.setBalance(new BigDecimal("500.43"));

        when(findAccount.byId(any())).thenReturn(account);

        changeBalanceAccount.removeValue(new BigDecimal("432.32"), 10L);

        assertEquals(new BigDecimal("68.11"), account.getBalance());

        verify(findAccount).byId(10L);
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("Dado um valor e um id da conta " +
                 "Quando chamado o removeValue e o usuario não tiver saldo " +
                 "Entao deve ser lançado um IlleagalArgumentException")
    void removeValueCase2() {
        Account account = new Account();
        account.setBalance(new BigDecimal("430.43"));

        when(findAccount.byId(any())).thenReturn(account);

        BigDecimal valor = new BigDecimal("432.32");
        assertThrows(IllegalArgumentException.class, () -> changeBalanceAccount.removeValue(valor, 10L));

        verify(findAccount).byId(10L);
        verify(accountRepository, never()).save(account);
    }
}