package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.AccountPaymentRequest;
import com.jwapp.financialapi.controller.dto.request.CardPaymentRequest;
import com.jwapp.financialapi.usecase.RegisterPayment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final RegisterPayment registerPayment;

    @PutMapping("/debt/{accountId}")
    public ResponseEntity<Void> putNewPaymentAtAccount(@PathVariable Long accountId, @Valid @RequestBody AccountPaymentRequest accountPaymentRequest) {
        Long idPayment = registerPayment.withPix(accountPaymentRequest.toDomain(accountId));
        return ResponseEntity.created(URI.create("/payments/" + idPayment)).build();
    }

    @PutMapping("/credit/{cardId}")
    public ResponseEntity<Void> putNewPaymentAtCard(@PathVariable Long cardId, @Valid @RequestBody CardPaymentRequest cardPaymentRequest) {
        registerPayment.withCreditCard(cardPaymentRequest.toDomain(cardId));
        return ResponseEntity.ok().build();
    }
}
