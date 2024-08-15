package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
