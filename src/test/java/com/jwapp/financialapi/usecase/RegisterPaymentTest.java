package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.domain.payment.CardPayment;
import com.jwapp.financialapi.repository.AccountPaymentRepository;
import com.jwapp.financialapi.repository.CardPaymentRepository;
import com.jwapp.financialapi.usecase.impl.RegisterPaymentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterPaymentTest {

    @Mock
    private AccountPaymentRepository accountPaymentRepository;
    @Mock
    private ChangeBalanceAccount changeBalanceAccount;
    @Mock
    private CardPaymentRepository cardPaymentRepository;
    @Mock
    private ChangeLimitCard changeLimitCard;

    @Captor
    private ArgumentCaptor<CardPayment> argumentCaptor;


    private RegisterPayment registerPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerPayment = new RegisterPaymentImpl(accountPaymentRepository, cardPaymentRepository, changeBalanceAccount, changeLimitCard);
    }

    @Test
    @DisplayName("Dado um AccountPayment com datetime, description e id da conta " +
                 "Quando chamado o withPix " +
                 "Então deve ser removido o saldo e salvo")
    void registerPaymentWithPixCase1() {
        AccountPayment accountPaymentBD = new AccountPayment();
        accountPaymentBD.setId(10L);
        when(accountPaymentRepository.save(any(AccountPayment.class))).thenReturn(accountPaymentBD);

        AccountPayment accountPayment = new AccountPayment(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(1L));
        Long idAccount = registerPayment.withPix(accountPayment);

        assertEquals(10L, idAccount);

        verify(changeBalanceAccount).removeValue(new BigDecimal("134.0"), 1L);
        verify(accountPaymentRepository).save(accountPayment);
    }

    @Test
    @DisplayName("Dado um AccountPayment com datetime, description e id da conta " +
                 "Quando chamado o withPix e der erro ao remover o valor " +
                 "Então o erro deve ser repassado")
    void registerPaymentWithCase2() {
        doThrow(IllegalArgumentException.class).when(changeBalanceAccount).removeValue(any(), any());

        AccountPayment accountPayment = new AccountPayment(null, new BigDecimal("134.0"), LocalDateTime.of(2024, 10,1, 10, 10), "teste", new Account(1L));
        assertThrows(IllegalArgumentException.class, () -> registerPayment.withPix(accountPayment));

        verify(changeBalanceAccount).removeValue(new BigDecimal("134.0"), 1L);
        verify(accountPaymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado um CardPayment com datetime, description, idCartao e parcelas " +
                 "Quando chamado o withCreditCard " +
                 "Então deve ser creditado parcelado")
    void withCreditCardCase1() {
        CardPayment cardPayment = new CardPayment(10L, new BigDecimal("134.43"), LocalDateTime.of(2024, 11, 10, 20, 20), "descricaoTeste", new Card(10L), 3);

        registerPayment.withCreditCard(cardPayment);

        verify(changeLimitCard).removeLimit(new BigDecimal("134.43"), 10L);
        verify(cardPaymentRepository, times(3)).save(argumentCaptor.capture());

        CardPayment cardPaymentInstallment1 = new CardPayment(null, new BigDecimal("44.81"), LocalDateTime.of(2024, 11, 10, 20, 20), "descricaoTeste (1/3)", new Card(10L), 3);
        CardPayment cardPaymentInstallment2 = new CardPayment(null, new BigDecimal("44.81"), LocalDateTime.of(2024, 12, 10, 20, 20), "descricaoTeste (2/3)", new Card(10L), 3);
        CardPayment cardPaymentInstallment3 = new CardPayment(null, new BigDecimal("44.81"), LocalDateTime.of(2025, 1, 10, 20, 20), "descricaoTeste (3/3)", new Card(10L), 3);

        assertEquals(cardPaymentInstallment1, argumentCaptor.getAllValues().get(0));
        assertEquals(cardPaymentInstallment2, argumentCaptor.getAllValues().get(1));
        assertEquals(cardPaymentInstallment3, argumentCaptor.getAllValues().get(2));
    }
}