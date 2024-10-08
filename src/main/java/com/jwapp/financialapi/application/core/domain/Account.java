package com.jwapp.financialapi.application.core.domain;

import com.jwapp.financialapi.application.core.domain.interfaces.ValueChange;
import com.jwapp.financialapi.application.core.domain.transaction.AccountTransaction;
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
@NoArgsConstructor
@AllArgsConstructor
public class Account implements ValueChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    private String bank;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<Card> cardList;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<AccountTransaction> accountPaymentList;

    public Account(Long id) {
        this.id = id;
    }

    @Override
    public boolean addValue(BigDecimal value) {
        this.balance = this.balance.add(value);
        return true;
    }

    @Override
    public boolean removeValue(BigDecimal value) {
        if (this.balance.subtract(value).compareTo(BigDecimal.ZERO) < 0)
            return false;
        this.balance = this.balance.subtract(value);
        return true;
    }

    @Override
    public BigDecimal getValue() {
        return this.balance;
    }
}
