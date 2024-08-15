package com.jwapp.financialapi.domain.receipt;

import com.jwapp.financialapi.domain.Card;
import com.jwapp.financialapi.domain.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReversalCardReceipt extends Transaction {

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;
}
