package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.CardRequest;
import com.jwapp.financialapi.usecase.CreateCard;
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

    private final CreateCard createCard;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CardRequest cardRequest) {
        Long id = createCard.createNew(cardRequest.toDomain());
        return ResponseEntity.created(URI.create("/cards/" + id)).build();
    }
}
