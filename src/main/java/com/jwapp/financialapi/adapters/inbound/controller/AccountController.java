package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.adapters.inbound.dto.request.AccountRequest;
import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.ports.CreateEntityPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final CreateEntityPort<Account> createAccount;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody AccountRequest accountRequest) {
        Long id = createAccount.createNew(accountRequest.toDomain());
        return ResponseEntity.created(URI.create("/accounts/" + id)).build();
    }
}
