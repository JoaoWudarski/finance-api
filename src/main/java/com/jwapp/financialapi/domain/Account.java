package com.jwapp.financialapi.domain;

import com.jwapp.financialapi.domain.payment.AccountPayment;
import com.jwapp.financialapi.domain.receipt.AccountReceipt;
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
public class Account {

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
    private List<AccountPayment> accountPaymentList;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<AccountReceipt> accountReceiptList;

    public Account(Long id) {
        this.id = id;
    }
}
