package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.ReceiveRequest;
import com.jwapp.financialapi.usecase.RegisterEntrance;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receives")
public class ReceiveController {

    private final RegisterEntrance registerEntrance;

    @PutMapping("/debt/{accountId}")
    public ResponseEntity<Void> putNewEntranceAtAccount(@PathVariable Long accountId, @Valid @RequestBody ReceiveRequest receiveRequest) {
        Long idEntrance = registerEntrance.withPix(receiveRequest.toDomain(accountId));
        return ResponseEntity.created(URI.create("/receives/" + idEntrance)).build();
    }
}
