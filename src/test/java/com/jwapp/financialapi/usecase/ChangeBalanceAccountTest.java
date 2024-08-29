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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}