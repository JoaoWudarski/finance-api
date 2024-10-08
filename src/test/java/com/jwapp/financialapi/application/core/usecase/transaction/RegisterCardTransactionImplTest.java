package com.jwapp.financialapi.application.core.usecase.transaction;

import com.jwapp.financialapi.application.core.domain.Card;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import com.jwapp.financialapi.application.ports.gateway.BasicEntityGatewayPort;
import com.jwapp.financialapi.application.ports.gateway.CardTransactionGatewayPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterCardTransactionImplTest {

    @Mock
    private BasicEntityGatewayPort<Card> channelTransactionGatewayPort;
    @Mock
    private CardTransactionGatewayPort cardTransactionGatewayPort;

    @Captor
    private ArgumentCaptor<CardTransaction> argumentCaptor;

    private RegisterCardTransactionImpl registerCardTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerCardTransaction = new RegisterCardTransactionImpl(channelTransactionGatewayPort, cardTransactionGatewayPort);
    }

    @Test
    @DisplayName("Dado um CardTransaction com datetime, description, idCartao e parcelas " +
            "Quando chamado o exit " +
            "Então deve ser creditado parcelado")
    void withCreditCardCase1() {
        CardTransaction cardTransaction = new CardTransaction(10L, new BigDecimal("134.43"), LocalDateTime.of(2024, 11, 10, 20, 20), "descricaoTeste", new Card(10L), 3);
        Card card = new Card(10L);
        card.setTotalLimit(new BigDecimal("1000.0"));
        card.setUsedLimit(new BigDecimal("231.43"));

        when(channelTransactionGatewayPort.findById(any())).thenReturn(Optional.of(card));

        registerCardTransaction.exit(cardTransaction);

        assertEquals(new BigDecimal("365.86"), card.getUsedLimit());

        verify(cardTransactionGatewayPort, times(3)).save(argumentCaptor.capture());
        verify(channelTransactionGatewayPort).findById(10L);
        verify(channelTransactionGatewayPort).save(card);

        CardTransaction cardTransactionInstallment1 = new CardTransaction(null, new BigDecimal("-44.81"), LocalDateTime.of(2024, 11, 10, 20, 20), "descricaoTeste (1/3)", new Card(10L), 3);
        CardTransaction cardTransactionInstallment2 = new CardTransaction(null, new BigDecimal("-44.81"), LocalDateTime.of(2024, 12, 10, 20, 20), "descricaoTeste (2/3)", new Card(10L), 3);
        CardTransaction cardTransactionInstallment3 = new CardTransaction(null, new BigDecimal("-44.81"), LocalDateTime.of(2025, 1, 10, 20, 20), "descricaoTeste (3/3)", new Card(10L), 3);

        assertEquals(cardTransactionInstallment1, argumentCaptor.getAllValues().get(0));
        assertEquals(cardTransactionInstallment2, argumentCaptor.getAllValues().get(1));
        assertEquals(cardTransactionInstallment3, argumentCaptor.getAllValues().get(2));
    }

    @Test
    @DisplayName("Dado um CardTransaction com datetime, description, idCartao e parcelas " +
                 "Quando chamado o withCreditCard mas nao tiver limite " +
                 "Então deve ser lancado um IllegalArgumentException")
    void withCreditCardCase2() {
        CardTransaction cardTransaction = new CardTransaction(10L, new BigDecimal("300.43"), LocalDateTime.of(2024, 11, 10, 20, 20), "descricaoTeste", new Card(10L), 3);
        Card card = new Card(10L);
        card.setTotalLimit(new BigDecimal("500.0"));
        card.setUsedLimit(new BigDecimal("231.43"));

        when(channelTransactionGatewayPort.findById(any())).thenReturn(Optional.of(card));

        assertThrows(IllegalArgumentException.class, () -> registerCardTransaction.exit(cardTransaction));

        verify(cardTransactionGatewayPort, never()).save(any());
        verify(channelTransactionGatewayPort).findById(10L);
    }
}