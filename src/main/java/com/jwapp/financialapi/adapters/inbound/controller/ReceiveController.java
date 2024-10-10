package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.adapters.inbound.dto.request.ReceiveRequest;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
import com.jwapp.financialapi.application.ports.RegisterTransactionPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receives")
public class ReceiveController {

    private final RegisterTransactionPort<AccountTransaction> registerAccountTransactionPort;

    @PutMapping("/debt/{accountId}")
    public ResponseEntity<Void> putNewEntranceAtAccount(@PathVariable Long accountId, @Valid @RequestBody ReceiveRequest receiveRequest) {
        Long idEntrance = registerAccountTransactionPort.entrance(receiveRequest.toDomain(accountId)).getId();
        return ResponseEntity.created(URI.create("/receives/" + idEntrance)).build();
    }
}
