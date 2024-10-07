package com.jwapp.financialapi.usecase.model;

import com.jwapp.financialapi.domain.Transaction;
import com.jwapp.financialapi.domain.payment.CardPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    private LocalDate initialDate;
    private LocalDate finalDate;
    private LocalDate maturityDate;
    private List<CardPayment> paymentList;

    public BigDecimal totalValue() {
        return paymentList.stream()
                .map(Transaction::getTransactionValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isClosed() {
        return LocalDate.now().isAfter(finalDate);
    }

    public String getPeriod() {
        DateTimeFormatter periodFormat = DateTimeFormatter.ofPattern("dd/MM");
        return initialDate.format(periodFormat) + " - " + finalDate.format(periodFormat);
    }
}
