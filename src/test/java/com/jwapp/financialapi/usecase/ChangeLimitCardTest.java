package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.repository.CardRepository;
import com.jwapp.financialapi.usecase.impl.ChangeLimitCardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChangeLimitCardTest {


    @Mock
    private CardRepository cardRepository;
    @Mock
    private FindCard findCard;

    private ChangeLimitCard changeLimitCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        changeLimitCard = new ChangeLimitCardImpl(cardRepository, findCard);
    }

    @Test
    @DisplayName("Dado um valor e um id do cartao " +
                 "Quando chamado o removeLimit " +
                 "Entao deve ser adicionado o valor utilizado do limite")
    void removeLimitCase1() {
        Card card = new Card(10L);
        card.setTotalLimit(new BigDecimal("1000.0"));
        card.setUsedLimit(new BigDecimal("231.43"));

        when(findCard.byId(any())).thenReturn(card);

        changeLimitCard.removeLimit(new BigDecimal("432.32"), 10L);

        assertEquals(new BigDecimal("663.75"), card.getUsedLimit());

        verify(findCard).byId(10L);
        verify(cardRepository).save(card);
    }

    @Test
    @DisplayName("Dado um valor e um id do cartao " +
                 "Quando chamado o removeLimit e nao tiver limite disponivel " +
                 "Entao deve ser lancado um IllegalArgumentException")
    void removeLimitCase2() {
        Card card = new Card(10L);
        card.setTotalLimit(new BigDecimal("500.0"));
        card.setUsedLimit(new BigDecimal("231.43"));

        when(findCard.byId(any())).thenReturn(card);

        BigDecimal valorCompra = new BigDecimal("432.32");
        assertThrows(IllegalArgumentException.class, () -> changeLimitCard.removeLimit(valorCompra, 10L));

        verify(findCard).byId(10L);
        verify(cardRepository, never()).save(any());
    }
}