package com.jwapp.financialapi.controller.dto.response;

import com.jwapp.financialapi.domain.Transaction;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public record TransactionResponse(
        String description,
        String dateTime,
        BigDecimal value
) {

    public static TransactionResponse build(Transaction transaction) {
        DateTimeFormatter brasilFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new TransactionResponse(transaction.getDescription(), transaction.getDateTime().format(brasilFormat), transaction.getTransactionValue());
    }
}
