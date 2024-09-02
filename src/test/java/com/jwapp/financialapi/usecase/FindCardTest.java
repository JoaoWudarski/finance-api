package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.exception.NotFoundException;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.impl.FindCardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FindCardTest {

    @Mock
    private CardRepository cardRepository;

    private FindCard findCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findCard = new FindCardImpl(cardRepository);
    }

    @Test
    @DisplayName("Dado um id de um cartao valido " +
                 "Quando chamado o byId " +
                 "Entao deve ser retornado um Card")
    void byIdCase1() {
        Card cardExpected = new Card(10L);
        when(cardRepository.findById(any())).thenReturn(Optional.of(cardExpected));

        Card card = findCard.byId(10L);

        assertEquals(cardExpected, card);
        verify(cardRepository).findById(10L);
    }

    @Test
    @DisplayName("Dado um id de um cartao valido " +
                 "Quando chamado o byId " +
                 "Entao deve ser retornado um Card")
    void byIdCase2() {
        when(cardRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findCard.byId(10L));

        verify(cardRepository).findById(10L);
    }
}