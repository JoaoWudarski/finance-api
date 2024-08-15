package com.jwapp.financialapi.controller;

import com.jwapp.financialapi.controller.dto.request.UserRequest;
import com.jwapp.financialapi.usecase.CreateUser;
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

    private final CreateUser createUser;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserRequest userRequest) {
        Long id = createUser.createNew(userRequest);
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }
}
