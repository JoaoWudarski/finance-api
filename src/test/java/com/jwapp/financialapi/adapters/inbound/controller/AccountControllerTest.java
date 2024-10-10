package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest extends FinancialApiApplicationTests {

    @Test
    @DisplayName("Dado um AccountRequest para cadastro " +
                 "Quando chamado o usecase createAccount " +
                 "Entao deve ser salvo na base e retornado 201")
    void createAccountCase1() throws Exception {
        when(userRepository.existsById(any())).thenReturn(Boolean.TRUE);
        when(accountRepository.findByUserAndBank(any(), any())).thenReturn(new ArrayList<>());
        when(accountRepository.save(any())).thenReturn(new Account(10L));

        MockHttpServletResponse mockHttpServletResponse = performPost("/accounts", HttpStatus.CREATED,
                "account/request/create-account-valid.json", "empty-file.json");

        assertEquals("/accounts/10", mockHttpServletResponse.getHeader("Location"));
        verify(accountRepository).save(Account.builder()
                .user(new User(3L))
                .bank("Santander")
                .balance(BigDecimal.ZERO)
                .build());
    }

    @Test
    @DisplayName("Dado um AccountRequest invalido para cadastro " +
                 "Quando chamado o usecase createAccount " +
                 "Entao deve ser lançado um erro de validação")
    void createAccountCase2() throws Exception {
        performPost("/accounts", HttpStatus.BAD_REQUEST,
                "account/request/create-account-invalid.json", "account/response/create-account-invalid.json");

        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado um AccountRequest para cadastro com um usuario que nao existe " +
                 "Quando chamado o usecase createAccount " +
                 "Entao deve ser lançado um 404")
    void createAccountCase3() throws Exception {
        when(userRepository.existsById(any())).thenReturn(Boolean.FALSE);

        performPost("/accounts", HttpStatus.NOT_FOUND,
                "account/request/create-account-valid.json", "account/response/create-account-usernotfound.json");

        verify(accountRepository, never()).save(any());
        verify(accountRepository, never()).findByUserAndBank(any(), any());
    }

    @Test
    @DisplayName("Dado um AccountRequest para cadastro com um banco ja cadastrado " +
                 "Quando chamado o usecase createAccount " +
                 "Entao deve ser lançado um 409")
    void createAccountCase4() throws Exception {
        when(userRepository.existsById(any())).thenReturn(Boolean.TRUE);
        when(accountRepository.findByUserAndBank(any(), any())).thenReturn(List.of(new Account()));

        performPost("/accounts", HttpStatus.CONFLICT,
                "account/request/create-account-valid.json", "account/response/create-account-bankexists.json");

        verify(accountRepository, never()).save(any());
    }
}