package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.controller.dto.request.AccountRequest;
import com.jwapp.financialapi.usecase.CreateAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest extends FinancialApiApplicationTests {

    @MockBean
    private CreateAccount createAccount;

    @Test
    @DisplayName("Dado uma nova conta " +
                 "Quando chamado o endpoint para gerar uma nova " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void saveCase1() throws Exception {
        when(createAccount.createNew(any())).thenReturn(10L);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .content("{\"bank\": \"Santander\", \"userId\": 3}")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/accounts/10", response.getHeader("Location"));

        verify(createAccount).createNew(new AccountRequest("Santander", 3L));
    }

    @ParameterizedTest
    @DisplayName("Dado um nova conta " +
                 "Quando chamado o endpoint para gerar uma nova mas sem passar as informacoes necessarias " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    @CsvSource(value = {
            "null, null",
            "\"\", null"
    },
    nullValues = {"null"})
    void saveCase2(String bank, Long userId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                        .content(String.format("{\"bank\": %s, \"userId\": %d}", bank, userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation request error"))
                .andExpect(jsonPath("$.body.bank").value("must not be empty"))
                .andExpect(jsonPath("$.body.userId").value("must not be null"));

        verify(createAccount, never()).createNew(any());
    }
}