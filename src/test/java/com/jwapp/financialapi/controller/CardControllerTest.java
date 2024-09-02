package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.usecase.CreateCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CardControllerTest extends FinancialApiApplicationTests {

    @MockBean
    private CreateCard createCard;

    @Test
    @DisplayName("Dado um novo cartao " +
                 "Quando chamado o endpoint para gerar um novo " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void saveCase1() throws Exception {
        when(createCard.createNew(any())).thenReturn(10L);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                .content(new StringBuilder()
                        .append("{\n")
                        .append("\"flag\": \"Visa\",\n")
                        .append("\"closeDay\": 10,\n")
                        .append("\"paymentDay\": \"20\",\n")
                        .append("\"totalLimit\": 2000.0,\n")
                        .append("\"accountId\": 3\n")
                        .append("}").toString())
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/cards/10", response.getHeader("Location"));

        verify(createCard).createNew(Card.builder().flagCard("Visa").closeDay(10).paymentDay(20).usedLimit(BigDecimal.ZERO).totalLimit(new BigDecimal("2000.0")).account(new Account(3L)).build());
    }

    @ParameterizedTest
    @DisplayName("Dado um novo cartao " +
                 "Quando chamado o endpoint para gerar um nov mas sem passar as informacoes necessarias " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    @CsvSource(value = {
            "null, -1, -1, -1, null",
            "\"\", 32, 32, null, null",
            "null, null, null, null, null"
    }, nullValues = {"null"})
    void saveCase2(String flag, Integer closeDay, Integer paymentDay, Integer totalLimit, Long accountId) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .content(String.format((new StringBuilder()
                                .append("{\n")
                                .append("\"flag\": %%s,\n")
                                .append("\"closeDay\": %%d,\n")
                                .append("\"paymentDay\": %%d,\n")
                                .append("\"totalLimit\": %%d,\n")
                                .append("\"accountId\": %%d\n")
                                .append("}").toString())
                                .formatted(), flag, closeDay, paymentDay, totalLimit, accountId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation request error"))
                .andExpect(jsonPath("$.body.flag").exists())
                .andExpect(jsonPath("$.body.closeDay").exists())
                .andExpect(jsonPath("$.body.paymentDay").exists())
                .andExpect(jsonPath("$.body.totalLimit").exists())
                .andExpect(jsonPath("$.body.accountId").exists());

        verify(createCard, never()).createNew(any());
    }
}