package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.impl.FindAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAccountTest {

    @Mock
    private AccountRepository accountRepository;

    private FindAccount findAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findAccount = new FindAccountImpl(accountRepository);
    }

    @Test
    @DisplayName("Dado um accountId que existe " +
                 "Quando chamado o usecase para validar se existe um account " +
                 "Entao deve ser retornado true")
    void existsCase1() {
        when(accountRepository.existsById(any())).thenReturn(true);

        boolean userExists = findAccount.exists(1L);

        assertTrue(userExists);

        verify(accountRepository).existsById(1L);
    }

    @Test
    @DisplayName("Dado um accountId que não existe " +
                 "Quando chamado o usecase para validar se existe um account " +
                 "Entao deve ser retornado false")
    void existsCase2() {
        when(accountRepository.existsById(any())).thenReturn(false);

        boolean userExists = findAccount.exists(1L);

        assertFalse(userExists);

        verify(accountRepository).existsById(1L);
    }
}