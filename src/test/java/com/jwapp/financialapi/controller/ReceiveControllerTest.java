package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.usecase.RegisterEntrance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReceiveControllerTest extends FinancialApiApplicationTests {

    @MockBean
    private RegisterEntrance registerEntrance;


    @Test
    @DisplayName("Dado uma nova transacao de entrada de saldo " +
                 "Quando chamado o endpoint para aumentar o saldo " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void transactionEntranceCase1() throws Exception {
        when(registerEntrance.withPix(any())).thenReturn(10L);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/receives/debt/4")
                .content(new StringBuilder()
                        .append("{")
                        .append("\"description\": \"Descricao de teste\",")
                        .append("\"value\": 3213.43")
                        .append("}").toString())
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/receives/10", response.getHeader("Location"));

        verify(registerEntrance).withPix(any());
    }

    @ParameterizedTest
    @DisplayName("Dado uma nova transacao de entrada de saldo com dados invalidos " +
                 "Quando chamado o endpoint para aumentar o saldo " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    @CsvSource(value = {
            "null, null",
            " , -1.0",
            "null, 0"
    }, nullValues = {"null"})
        void transactionEntranceFailedCase2(String description, Float value) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/receives/debt/4")
                        .content(String.format(Locale.ENGLISH, (new StringBuilder()
                                .append("{\n")
                                .append("\"description\": %%s,\n")
                                .append("\"value\": %%f\n")
                                .append("}").toString())
                                .formatted(), description, value))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation request error"))
                .andExpect(jsonPath("$.body.description").exists())
                .andExpect(jsonPath("$.body.value").exists());

        verify(registerEntrance, never()).withPix(any());
    }
}