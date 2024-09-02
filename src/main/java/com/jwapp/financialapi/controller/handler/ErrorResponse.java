package com.jwapp.financialapi.controller.handler;

public record ErrorResponse(
        String message,
        Object body
) {
}
