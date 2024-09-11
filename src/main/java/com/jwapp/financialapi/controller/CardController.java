package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.CardRequest;
import com.jwapp.financialapi.controller.dto.response.CreditCardBillResponse;
import com.jwapp.financialapi.usecase.CreateCard;
import com.jwapp.financialapi.usecase.FindBill;
import com.jwapp.financialapi.usecase.model.Bill;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CreateCard createCard;
    private final FindBill findBill;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CardRequest cardRequest) {
        Long id = createCard.createNew(cardRequest.toDomain());
        return ResponseEntity.created(URI.create("/cards/" + id)).build();
    }

    @GetMapping("/bill/{cardId}")
    public ResponseEntity<CreditCardBillResponse> getBill(@PathVariable Long cardId, @RequestParam(required = false) Long minusMonths) {
        Bill bill = findBill.byCardIdAndMonths(cardId, minusMonths);
        return ResponseEntity.ok(CreditCardBillResponse.build(bill));
    }
}
