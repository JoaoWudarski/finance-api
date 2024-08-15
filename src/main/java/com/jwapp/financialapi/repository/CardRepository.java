package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
