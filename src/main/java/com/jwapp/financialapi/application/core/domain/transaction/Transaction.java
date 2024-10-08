package com.jwapp.financialapi.application.core.domain.transaction;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Inheritance
@AllArgsConstructor
@NoArgsConstructor
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected BigDecimal transactionValue;
    protected LocalDateTime dateTime;
    protected String description;
}
