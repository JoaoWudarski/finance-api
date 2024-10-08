package com.jwapp.financialapi.application.core.domain.transaction;

import com.jwapp.financialapi.application.core.domain.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountTransaction extends Transaction {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public AccountTransaction(Long id, BigDecimal transactionValue, LocalDateTime dateTime, String description, Account account) {
        super(id, transactionValue, dateTime, description);
        this.account = account;
    }
}
