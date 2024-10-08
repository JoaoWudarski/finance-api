package com.jwapp.financialapi.application.core.usecase.account;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.gateway.AccountGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindAccountImplTest {

    @Mock
    private AccountGatewayPort accountGatewayPort;

    private FindAccountImpl findAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findAccount = new FindAccountImpl(accountGatewayPort);
    }

    @Test
    @DisplayName("Dado um accountId que existe " +
            "Quando chamado o usecase para validar se existe um account " +
            "Entao deve ser retornado true")
    void existsCase1() {
        when(accountGatewayPort.existsById(any())).thenReturn(true);

        boolean userExists = findAccount.exists(1L);

        assertTrue(userExists);

        verify(accountGatewayPort).existsById(1L);
    }

    @Test
    @DisplayName("Dado um accountId que não existe " +
            "Quando chamado o usecase para validar se existe um account " +
            "Entao deve ser retornado false")
    void existsCase2() {
        when(accountGatewayPort.existsById(any())).thenReturn(false);

        boolean userExists = findAccount.exists(1L);

        assertFalse(userExists);

        verify(accountGatewayPort).existsById(1L);
    }

    @Test
    @DisplayName("Dado um accountId valido " +
            "Quando chamado o usecase para buscar o account " +
            "Entao deve ser o account encontrado")
    void findByIdCase1() {
        Account accountBd = new Account(1L);
        when(accountGatewayPort.findById(any())).thenReturn(Optional.of(accountBd));

        Account account = findAccount.byId(1L);

        assertEquals(accountBd, account);

        verify(accountGatewayPort).findById(1L);
    }

    @Test
    @DisplayName("Dado um accountId que nao exista " +
            "Quando chamado o usecase para buscar o account " +
            "Entao deve ser lancado um NotFoundException")
    void findByIdCase2() {
        when(accountGatewayPort.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findAccount.byId(1L));

        verify(accountGatewayPort).findById(1L);
    }
}