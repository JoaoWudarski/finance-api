package com.jwapp.financialapi.adapters.outbound.repository;

import com.jwapp.financialapi.application.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
