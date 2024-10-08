package com.jwapp.financialapi.application.core.usecase.account;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.core.exception.ConflictException;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.AccountGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAccountImplTest {

    @Mock
    private AccountGatewayPort accountGatewayPort;
    @Mock
    private FindEntityPort<User, Long> findUserPort;

    private CreateAccountImpl createAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createAccount = new CreateAccountImpl(accountGatewayPort, findUserPort);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
            "Quando chamado o usecase para criar um novo account " +
            "Entao deve ser salvo no banco de dados e retornado o novo ID")
    void createNewCase1() {
        Account accountDb = new Account(10L, new BigDecimal("1000.0"), "Santander", null, null, null);
        when(accountGatewayPort.save(Mockito.any(Account.class))).thenReturn(accountDb);
        when(accountGatewayPort.findByUserAndBank(any(), any())).thenReturn(new ArrayList<>());
        when(findUserPort.exists(any())).thenReturn(true);

        Long id = createAccount.createNew(Account.builder().bank("Santander").balance(BigDecimal.ZERO).user(new User(3L)).build());

        assertEquals(10L, id);
        verify(accountGatewayPort).save(new Account(null, BigDecimal.ZERO, "Santander", new User(3L), null, null));
        verify(accountGatewayPort).findByUserAndBank(new User(3L), "Santander");
        verify(findUserPort).exists(3L);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
            "Quando chamado o usecase para criar um novo account e já existir uma conta com esse banco " +
            "Entao deve ser lançado um ConflictException")
    void createNewCase2() {
        when(accountGatewayPort.findByUserAndBank(any(), any())).thenReturn(List.of(new Account(1L, null, "Santander", null, null, null)));
        when(findUserPort.exists(any())).thenReturn(true);

        Account account = Account.builder().bank("Santander").user(new User(3L)).build();
        assertThrows(ConflictException.class, () -> createAccount.createNew(account));

        verify(accountGatewayPort).findByUserAndBank(new User(3L), "Santander");
        verify(accountGatewayPort, never()).save(any());
        verify(findUserPort).exists(3L);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
            "Quando chamado o usecase para criar um novo account e o User nao existir " +
            "Entao deve ser lançado um NotFound")
    void createNewCase3() {
        when(accountGatewayPort.findByUserAndBank(any(), any())).thenReturn(List.of(new Account(1L, null, "Santander", null, null, null)));
        when(findUserPort.exists(any())).thenReturn(false);

        Account account = Account.builder().bank("Santander").user(new User(3L)).build();
        assertThrows(NotFoundException.class, () -> createAccount.createNew(account));

        verify(accountGatewayPort, never()).findByUserAndBank(any(), any());
        verify(findUserPort).exists(3L);
        verify(accountGatewayPort, never()).save(any());
    }
}