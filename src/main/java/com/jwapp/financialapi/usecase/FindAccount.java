package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;

public interface FindAccount {

    boolean exists(Long id);
    Account byId(Long id);
}
