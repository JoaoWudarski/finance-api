package com.jwapp.financialapi.repository;

import com.jwapp.financialapi.domain.receipt.AccountReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountReceiptRepository extends JpaRepository<AccountReceipt, Long> {
}
