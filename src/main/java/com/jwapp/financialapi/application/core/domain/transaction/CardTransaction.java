package com.jwapp.financialapi.application.core.domain.transaction;

import com.jwapp.financialapi.application.core.domain.Card;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardTransaction extends Transaction {

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;
    private Integer installments;

    public CardTransaction(Long id, BigDecimal transactionValue, LocalDateTime dateTime, String description, Card card, Integer installments) {
        super(id, transactionValue, dateTime, description);
        this.card = card;
        this.installments = installments;
    }

    public BigDecimal createInstallmentValue() {
        return super.transactionValue.divide(new BigDecimal(installments), 2, RoundingMode.HALF_EVEN);
    }

    public CardTransaction(CardTransaction cardTransaction) {
        super(cardTransaction.getId(), cardTransaction.getTransactionValue(), cardTransaction.getDateTime(), cardTransaction.getDescription());
        this.card = cardTransaction.getCard();
        this.installments = cardTransaction.getInstallments();
    }
}