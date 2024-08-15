package com.jwapp.financialapi.domain.payment;

import com.jwapp.financialapi.domain.CurrentAccount;
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
public class CurrentAccountPayment extends Transaction {

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private CurrentAccount currentAccount;
}
