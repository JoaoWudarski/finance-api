package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.application.core.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest extends FinancialApiApplicationTests {

    @Test
    @DisplayName("Dado um UserRequest para cadastro " +
                 "Quando chamado o usecase createUser " +
                 "Entao deve ser salvo na base e retornado 201")
    void createUserCase1() throws Exception {
        when(userRepository.save(any())).thenReturn(new User(10L));

        MockHttpServletResponse mockHttpServletResponse = performPost("/users", HttpStatus.CREATED,
                "user/request/create-user-valid.json", "empty-file.json");

        assertEquals("/users/10", mockHttpServletResponse.getHeader("Location"));
        verify(userRepository).save(User.builder()
                .name("Joao")
                .build());
    }

    @Test
    @DisplayName("Dado um CardRequest invalido para cadastro " +
                 "Quando chamado o usecase createCard " +
                 "Entao deve ser lançado um erro de validação")
    void createCardCase2() throws Exception {
        performPost("/users", HttpStatus.BAD_REQUEST,
                "user/request/create-user-invalid.json", "user/response/create-user-invalid.json");

        verify(accountRepository, never()).save(any());
    }
}