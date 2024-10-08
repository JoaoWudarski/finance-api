package com.jwapp.financialapi.application.core.usecase.card;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.CardGatewayPort;
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
import static org.mockito.Mockito.verify;

class CreateCardImplTest {

    @Mock
    private CardGatewayPort cardGatewayPort;
    
    @Mock
    private FindEntityPort<Account, Long> findAccount;

    private CreateCardImpl createCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createCard = new CreateCardImpl(cardGatewayPort, findAccount);
    }

    @Test
    @DisplayName("Dado um CardRequest " +
            "Quando chamado o usecase para criar um novo card " +
            "Entao deve ser salvo no banco de dados e retornado o novo ID")
    void createNewCase1() {
        Card newCard = Card.builder().flagCard("Visa").closeDay(10).paymentDay(20).totalLimit(new BigDecimal("3000.0")).account(new Account(3L)).build();
        Card cardDb = new Card(1L);
        when(cardGatewayPort.save(Mockito.any())).thenReturn(cardDb);
        when(findAccount.exists(any())).thenReturn(true);

        Long id = createCard.createNew(newCard);

        assertEquals(1L, id);
        verify(cardGatewayPort).save(newCard);
        verify(findAccount).exists(3L);
    }

    @Test
    @DisplayName("Dado um CardRequest " +
            "Quando chamado o usecase para criar um novo card e a Account nao existir " +
            "Entao deve ser lançado um NotFound")
    void createNewCase2() {
        when(findAccount.exists(any())).thenReturn(false);

        Card newCard = Card.builder().flagCard("Visa").closeDay(10).paymentDay(20).totalLimit(new BigDecimal("3000.0")).account(new Account(3L)).build();
        assertThrows(NotFoundException.class, () -> createCard.createNew(newCard));

        verify(cardGatewayPort, never()).save(any());
        verify(findAccount).exists(3L);
    }
}