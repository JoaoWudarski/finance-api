package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.User;
import com.jwapp.financialapi.exception.ConflictException;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.AccountRepository;
import com.jwapp.financialapi.usecase.impl.CreateAccountImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private FindUser findUser;

    private CreateAccount createAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createAccount = new CreateAccountImpl(accountRepository, findUser);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
                 "Quando chamado o usecase para criar um novo account " +
                 "Entao deve ser salvo no banco de dados e retornado o novo ID")
    void createNewCase1() {
        Account accountDb = new Account(10L, new BigDecimal("1000.0"), "Santander", null, null, null, null);
        when(accountRepository.save(Mockito.any(Account.class))).thenReturn(accountDb);
        when(accountRepository.findByUserAndBank(any(), any())).thenReturn(new ArrayList<>());
        when(findUser.exists(any())).thenReturn(true);

        Long id = createAccount.createNew(Account.builder().bank("Santander").balance(BigDecimal.ZERO).user(new User(3L)).build());

        assertEquals(10L, id);
        verify(accountRepository).save(new Account(null, BigDecimal.ZERO, "Santander", new User(3L), null, null, null));
        verify(accountRepository).findByUserAndBank(new User(3L), "Santander");
        verify(findUser).exists(3L);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
                 "Quando chamado o usecase para criar um novo account e já existir uma conta com esse banco " +
                 "Entao deve ser lançado um ConflictException")
    void createNewCase2() {
        when(accountRepository.findByUserAndBank(any(), any())).thenReturn(List.of(new Account(1L, null, "Santander", null, null, null, null)));
        when(findUser.exists(any())).thenReturn(true);

        Account account = Account.builder().bank("Santander").user(new User(3L)).build();
        assertThrows(ConflictException.class, () -> createAccount.createNew(account));

        verify(accountRepository).findByUserAndBank(new User(3L), "Santander");
        verify(accountRepository, never()).save(any());
        verify(findUser).exists(3L);
    }

    @Test
    @DisplayName("Dado um AccountRequest " +
                 "Quando chamado o usecase para criar um novo account e o User nao existir " +
                 "Entao deve ser lançado um NotFound")
    void createNewCase3() {
        when(accountRepository.findByUserAndBank(any(), any())).thenReturn(List.of(new Account(1L, null, "Santander", null, null, null, null)));
        when(findUser.exists(any())).thenReturn(false);

        Account account = Account.builder().bank("Santander").user(new User(3L)).build();
        assertThrows(NotFoundException.class, () -> createAccount.createNew(account));

        verify(accountRepository, never()).findByUserAndBank(any(), any());
        verify(findUser).exists(3L);
        verify(accountRepository, never()).save(any());
    }
}