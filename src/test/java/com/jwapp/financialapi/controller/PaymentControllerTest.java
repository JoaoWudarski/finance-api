package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.usecase.RegisterPayment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest extends FinancialApiApplicationTests {

    @MockBean
    private RegisterPayment registerPayment;


    @Test
    @DisplayName("Dado uma nova transacao de saida de saldo " +
                 "Quando chamado o endpoint para diminuir o saldo " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void transactionPaymentCase1() throws Exception {
        when(registerPayment.withPix(any())).thenReturn(10L);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/payments/debt/4")
                .content(new StringBuilder()
                        .append("{")
                        .append("\"description\": \"Descricao de teste\",")
                        .append("\"value\": 3213.43")
                        .append("}").toString())
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/payments/10", response.getHeader("Location"));

        verify(registerPayment).withPix(any());
    }

    @ParameterizedTest
    @DisplayName("Dado uma nova transacao de saida de saldo com dados invalidos " +
                 "Quando chamado o endpoint para diminuir o saldo " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    @CsvSource(value = {
            "null, null",
            " , -1.0",
            "null, 0"
    }, nullValues = {"null"})
        void transactionPaymentFailedCase2(String description, Float value) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/payments/debt/4")
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

        verify(registerPayment, never()).withPix(any());
    }

    @Test
    @DisplayName("Dado uma nova transacao de saida de cartao de credito " +
                 "Quando chamado o endpoint para pagamento com cartao " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void transactionPaymentCardCase1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/payments/credit/4")
                .content(new StringBuilder()
                        .append("{")
                        .append("\"installments\": 5,")
                        .append("\"description\": \"Descricao de teste\",")
                        .append("\"value\": 3213.43")
                        .append("}").toString())
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(200, response.getStatus());

        verify(registerPayment).withCreditCard(any());
    }

    @ParameterizedTest
    @DisplayName("Dado uma nova transacao de pagamento com cartao de credito " +
                 "Quando chamado o endpoint para pagar " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    @CsvSource(value = {
            "null, null, null",
            " , -1.0, -1",
            "null, 0, 0"
    }, nullValues = {"null"})
    void transactionPaymentCardFailedCase2(String description, Float value, Integer installments) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/payments/credit/4")
                        .content(String.format(Locale.ENGLISH, (new StringBuilder()
                                .append("{\n")
                                .append("\"installments\": %%d,")
                                .append("\"description\": %%s,\n")
                                .append("\"value\": %%f\n")
                                .append("}").toString())
                                .formatted(), installments, description, value))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation request error"))
                .andExpect(jsonPath("$.body.installments").exists())
                .andExpect(jsonPath("$.body.description").exists())
                .andExpect(jsonPath("$.body.value").exists());

        verify(registerPayment, never()).withCreditCard(any());
    }
}