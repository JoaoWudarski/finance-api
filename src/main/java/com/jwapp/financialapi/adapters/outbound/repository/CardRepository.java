package com.jwapp.financialapi.adapters.outbound.repository;

import com.jwapp.financialapi.application.core.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
