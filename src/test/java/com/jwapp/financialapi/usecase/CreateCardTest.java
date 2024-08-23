package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.controller.dto.request.CardRequest;
import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.impl.CreateCardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateCardTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private FindAccount findAccount;

    private CreateCard createCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createCard = new CreateCardImpl(cardRepository, findAccount);
    }

    @Test
    @DisplayName("Dado um CardRequest " +
                 "Quando chamado o usecase para criar um novo card " +
                 "Entao deve ser salvo no banco de dados e retornado o novo ID")
    void createNewCase1() {
        Card card = new Card(1L);
        when(cardRepository.save(Mockito.any())).thenReturn(card);
        when(findAccount.exists(any())).thenReturn(true);

        Long id = createCard.createNew(new CardRequest("Visa", 10, 20, new BigDecimal("3000.0"), 3L));

        assertEquals(1L, id);
        verify(cardRepository).save(new Card(null, "Visa", 10, 20, new BigDecimal("3000.0"), BigDecimal.ZERO, new Account(3L), null, null));
        verify(findAccount).exists(3L);
    }

    @Test
    @DisplayName("Dado um CardRequest " +
                 "Quando chamado o usecase para criar um novo card e a Account nao existir " +
                 "Entao deve ser lançado um NotFound")
    void createNewCase2() {
        when(findAccount.exists(any())).thenReturn(false);

        CardRequest cardRequest = new CardRequest("Visa", 10, 20, new BigDecimal("3000.0"), 3L);
        assertThrows(NotFoundException.class, () -> createCard.createNew(cardRequest));

        verify(cardRepository, never()).save(any());
        verify(findAccount).exists(3L);
    }

}