package com.jwapp.financialapi.adapters.inbound.handler;

public record ErrorResponse(
        String message,
        Object body
) {
}
