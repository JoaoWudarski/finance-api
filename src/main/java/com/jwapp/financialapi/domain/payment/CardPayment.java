package com.jwapp.financialapi.domain.payment;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.Transaction;
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
public class CardPayment extends Transaction {

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;
    private Integer installments;

    public CardPayment(Long id, BigDecimal transactionValue, LocalDateTime dateTime, String description, Card card, Integer installments) {
        super(id, transactionValue, dateTime, description);
        this.card = card;
        this.installments = installments;
    }

    public BigDecimal createInstallmentValue() {
        return super.transactionValue.divide(new BigDecimal(installments), 2, RoundingMode.HALF_EVEN);
    }

    public CardPayment(CardPayment cardPayment) {
        super(cardPayment.getId(), cardPayment.getTransactionValue(), cardPayment.getDateTime(), cardPayment.getDescription());
        this.card = cardPayment.getCard();
        this.installments = cardPayment.getInstallments();
    }
}
