package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.FinancialApiApplicationTests;
import com.jwapp.financialapi.domain.User;
import com.jwapp.financialapi.usecase.CreateUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest extends FinancialApiApplicationTests {

    @MockBean
    private CreateUser createUser;

    @Test
    @DisplayName("Dado um novo usuario " +
                 "Quando chamado o endpoint para gerar um novo " +
                 "Entao deve ser chamado o serviço e retornado 201 com o ID gerado")
    void saveCase1() throws Exception {
        when(createUser.createNew(Mockito.any())).thenReturn(10L);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content("{\"name\": \"Joao\"}")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(201, response.getStatus());
        assertEquals("/users/10", response.getHeader("Location"));

        verify(createUser).createNew(User.builder().name("Joao").build());
    }

    @Test
    @DisplayName("Dado um novo usuario " +
                 "Quando chamado o endpoint para gerar um novo mas sem passar as informacoes necessarias " +
                 "Entao deve ser retornado um erro 400 indicando os campos faltantes")
    void saveCase2() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content("{\"name\": \"\"}")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Validation request error\",\"body\":{\"name\":\"must not be empty\"}}", response.getContentAsString());

        response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content("{\"name\": null}")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(400, response.getStatus());
        assertEquals("{\"message\":\"Validation request error\",\"body\":{\"name\":\"must not be empty\"}}", response.getContentAsString());
    }
}