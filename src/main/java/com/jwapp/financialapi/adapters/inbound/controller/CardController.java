package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.adapters.inbound.dto.request.CardRequest;
import com.jwapp.financialapi.application.core.domain.Card;
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
@RequestMapping("/cards")
public class CardController {

    private final CreateEntityPort<Card> createCardPort;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CardRequest cardRequest) {
        Long id = createCardPort.createNew(cardRequest.toDomain());
        return ResponseEntity.created(URI.create("/cards/" + id)).build();
    }
}
