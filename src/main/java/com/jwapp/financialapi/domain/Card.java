package com.jwapp.financialapi.domain;

import com.jwapp.financialapi.domain.payment.CardPayment;
import com.jwapp.financialapi.domain.receipt.ReversalCardReceipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bank;
    private String flagCard;
    private Integer closeDay;
    private Integer paymentDay;
    private BigDecimal totalLimit;
    private BigDecimal usedLimit;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<CardPayment> cardPaymentList;
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    private List<ReversalCardReceipt> reversalCardReceiptList;
}
