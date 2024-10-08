package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.adapters.inbound.dto.request.AccountPaymentRequest;
import com.jwapp.financialapi.adapters.inbound.dto.request.CardPaymentRequest;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import com.jwapp.financialapi.application.ports.RegisterTransactionPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final RegisterTransactionPort<CardTransaction> registerCardTransactionPort;
    private final RegisterTransactionPort<AccountTransaction> registerAccountTransactionPort;

    @PutMapping("/debt/{accountId}")
    public ResponseEntity<Void> putNewPaymentAtAccount(@PathVariable Long accountId, @Valid @RequestBody AccountPaymentRequest accountPaymentRequest) {
        Long idPayment = registerAccountTransactionPort.exit(accountPaymentRequest.toDomain(accountId)).getId();
        return ResponseEntity.created(URI.create("/payments/" + idPayment)).build();
    }

    @PutMapping("/credit/{cardId}")
    public ResponseEntity<Void> putNewPaymentAtCard(@PathVariable Long cardId, @Valid @RequestBody CardPaymentRequest cardPaymentRequest) {
        registerCardTransactionPort.exit(cardPaymentRequest.toDomain(cardId));
        return ResponseEntity.ok().build();
    }
}
