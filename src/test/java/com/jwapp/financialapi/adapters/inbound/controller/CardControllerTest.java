package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class CardControllerTest extends FinancialApiApplicationTests {

    @Test
    @DisplayName("Dado um CardRequest para cadastro " +
                 "Quando chamado o usecase createCard " +
                 "Entao deve ser salvo na base e retornado 201")
    void createCardCase1() throws Exception {
        when(accountRepository.existsById(any())).thenReturn(Boolean.TRUE);
        when(cardRepository.save(any())).thenReturn(new Card(10L));

        MockHttpServletResponse mockHttpServletResponse = performPost("/cards", HttpStatus.CREATED,
                "card/request/create-card-valid.json", "empty-file.json");

        assertEquals("/cards/10", mockHttpServletResponse.getHeader("Location"));
        verify(cardRepository).save(Card.builder()
                .flagCard("Visa")
                .closeDay(10)
                .paymentDay(20)
                .totalLimit(new BigDecimal("1000.30"))
                .usedLimit(BigDecimal.ZERO)
                .account(new Account(3L))
                .build());
    }

    @Test
    @DisplayName("Dado um CardRequest invalido para cadastro " +
                 "Quando chamado o usecase createCard " +
                 "Entao deve ser lançado um erro de validação")
    void createCardCase2() throws Exception {
        performPost("/cards", HttpStatus.BAD_REQUEST,
                "card/request/create-card-invalid.json", "card/response/create-card-invalid.json");

        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado um CardRequest para cadastro com uma conta que nao existe " +
                 "Quando chamado o usecase createCard " +
                 "Entao deve ser lançado um 404")
    void createCardCase3() throws Exception {
        when(accountRepository.existsById(any())).thenReturn(Boolean.FALSE);

        performPost("/cards", HttpStatus.NOT_FOUND,
                "card/request/create-card-valid.json", "card/response/create-card-accountnotfound.json");

        verify(cardRepository, never()).save(any());
        verify(accountRepository).existsById(3L);
    }
}