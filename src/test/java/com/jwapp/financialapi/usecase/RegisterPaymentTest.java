package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.repository.AccountPaymentRepository;
import com.jwapp.financialapi.usecase.impl.RegisterPaymentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    private RegisterPayment registerPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerPayment = new RegisterPaymentImpl(accountPaymentRepository, changeBalanceAccount);
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
}