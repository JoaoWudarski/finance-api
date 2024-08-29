package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.PaymentRequest;
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
    public ResponseEntity<Void> putNewPaymentAtAccount(@PathVariable Long accountId, @Valid @RequestBody PaymentRequest paymentRequest) {
        Long idPayment = registerPayment.withPix(paymentRequest.toDomain(accountId));
        return ResponseEntity.created(URI.create("/payments/" + idPayment)).build();
    }
}
