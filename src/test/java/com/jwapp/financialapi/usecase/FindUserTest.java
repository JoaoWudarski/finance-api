package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.repository.UserRepository;
import com.jwapp.financialapi.usecase.impl.FindUserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindUserTest {

    @Mock
    private UserRepository userRepository;

    private FindUser findUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findUser = new FindUserImpl(userRepository);
    }

    @Test
    @DisplayName("Dado um userId que existe " +
                 "Quando chamado o usecase para validar se existe um usuario " +
                 "Entao deve ser retornado true")
    void existsCase1() {
        when(userRepository.existsById(any())).thenReturn(true);

        boolean userExists = findUser.exists(1L);

        assertTrue(userExists);

        verify(userRepository).existsById(1L);
    }

    @Test
    @DisplayName("Dado um userId que não existe " +
                 "Quando chamado o usecase para validar se existe um usuario " +
                 "Entao deve ser retornado false")
    void existsCase2() {
        when(userRepository.existsById(any())).thenReturn(false);

        boolean userExists = findUser.exists(1L);

        assertFalse(userExists);

        verify(userRepository).existsById(1L);
    }
}
