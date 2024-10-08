package com.jwapp.financialapi.application.ports.gateway;

import com.jwapp.financialapi.application.core.domain.Account;
import com.jwapp.financialapi.application.core.domain.User;

import java.util.List;

public interface AccountGatewayPort extends BasicEntityGatewayPort<Account> {

    List<Account> findByUserAndBank(User user, String bank);
    Boolean existsById(Long id);
}
