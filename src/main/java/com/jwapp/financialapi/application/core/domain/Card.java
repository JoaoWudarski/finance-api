package com.jwapp.financialapi.application.core.domain;

import com.jwapp.financialapi.application.core.domain.interfaces.ValueChange;
import com.jwapp.financialapi.application.core.domain.transaction.CardTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card implements ValueChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flagCard;
    private Integer closeDay;
    private Integer paymentDay;
    private BigDecimal totalLimit;
    private BigDecimal usedLimit;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<CardTransaction> cardPaymentList;

    public Card(Long id) {
        this.id = id;
    }


    @Override
    public boolean addValue(BigDecimal value) {
        return true;
    }

    @Override
    public boolean removeValue(BigDecimal value) {
        if (this.usedLimit.add(value).compareTo(totalLimit) > 0)
            return false;
        this.usedLimit = this.usedLimit.add(value);
        return true;
    }

    @Override
    public BigDecimal getValue() {
        return this.usedLimit;
    }
}
