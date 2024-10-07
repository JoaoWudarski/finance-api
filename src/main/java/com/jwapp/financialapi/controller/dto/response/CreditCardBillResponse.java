package com.jwapp.financialapi.controller.dto.response;

import com.jwapp.financialapi.usecase.model.Bill;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.jwapp.financialapi.utils.DateUtils.toBrazilFormat;

public record CreditCardBillResponse(
        String period,
        BigDecimal totalValue,
        String maturityDate,
        Boolean isClosed,
        Integer paymentHash,
        List<TransactionResponse> payments
) {

    public static CreditCardBillResponse build(Bill bill) {
        BigDecimal totalValue = bill.totalValue();
        int paymentHash = Objects.hash(bill.getPeriod(), totalValue, bill.getMaturityDate());
        List<TransactionResponse> transactionResponseList = bill.getPaymentList().stream()
                .map(TransactionResponse::build).toList();

        return new CreditCardBillResponse(
                bill.getPeriod(),
                totalValue,
                toBrazilFormat(bill.getMaturityDate()),
                bill.isClosed(),
                bill.isClosed() ? paymentHash : null,
                transactionResponseList);
    }
}
