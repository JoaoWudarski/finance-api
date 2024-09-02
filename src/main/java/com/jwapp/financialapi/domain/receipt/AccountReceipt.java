package com.jwapp.financialapi.domain.receipt;

import com.jwapp.financialapi.domain.Account;
import com.jwapp.financialapi.domain.Transaction;
import jakarta.persistence.*;
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
public class AccountReceipt extends Transaction {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public AccountReceipt(Long id, BigDecimal transactionValue, LocalDateTime dateTime, String description, Account account) {
        super(id, transactionValue, dateTime, description);
        this.account = account;
    }
}
