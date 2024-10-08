package com.jwapp.financialapi.adapters.inbound.controller;

import com.jwapp.financialapi.adapters.inbound.dto.request.UserRequest;
import com.jwapp.financialapi.application.core.domain.User;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateEntityPort<User> createUserPort;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserRequest userRequest) {
        Long id = createUserPort.createNew(userRequest.toDomain());
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }
}
