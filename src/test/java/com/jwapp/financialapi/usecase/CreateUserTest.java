package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.User;
import com.jwapp.financialapi.repository.UserRepository;
import com.jwapp.financialapi.usecase.impl.CreateUserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserTest {

    @Mock
    private UserRepository userRepository;

    private CreateUser createUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUser = new CreateUserImpl(userRepository);
    }

    @Test
    @DisplayName("Dado um UserRequest" +
                 "Quando chamado o usecase para criar um novo usuario " +
                 "Entao deve ser salvo no banco de dados e retornado o novo ID")
    void createNewCase1() {
        User newUser = new User(null, "Joao", null);
        User userDb = new User(10L, "Joao", null);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(userDb);

        Long id = createUser.createNew(newUser);

        assertEquals(10L, id);
        verify(userRepository).save(newUser);
    }
}