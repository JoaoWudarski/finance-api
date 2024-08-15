package com.jwapp.financialapi.domain;

import com.jwapp.financialapi.domain.payment.CurrentAccountPayment;
import com.jwapp.financialapi.domain.receipt.CurrentAccountReceipt;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class CurrentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    private String bank;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "currentAccount", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<CurrentAccountPayment> currentAccountPaymentList;
    @OneToMany(mappedBy = "currentAccount", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<CurrentAccountReceipt> currentAccountReceiptList;
}
