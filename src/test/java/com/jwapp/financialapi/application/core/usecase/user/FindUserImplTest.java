package com.jwapp.financialapi.application.core.usecase.user;

import com.jwapp.financialapi.application.ports.gateway.UserGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindUserImplTest {

    @Mock
    private UserGatewayPort userGatewayPort;

    private FindUserImpl findUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findUser = new FindUserImpl(userGatewayPort);
    }

    @Test
    @DisplayName("Dado um userId que existe " +
            "Quando chamado o usecase para validar se existe um usuario " +
            "Entao deve ser retornado true")
    void existsCase1() {
        when(userGatewayPort.existsById(any())).thenReturn(true);

        boolean userExists = findUser.exists(1L);

        assertTrue(userExists);

        verify(userGatewayPort).existsById(1L);
    }

    @Test
    @DisplayName("Dado um userId que não existe " +
            "Quando chamado o usecase para validar se existe um usuario " +
            "Entao deve ser retornado false")
    void existsCase2() {
        when(userGatewayPort.existsById(any())).thenReturn(false);

        boolean userExists = findUser.exists(1L);

        assertFalse(userExists);

        verify(userGatewayPort).existsById(1L);
    }
}