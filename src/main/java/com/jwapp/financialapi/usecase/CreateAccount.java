package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.Account;

public interface CreateAccount {

    Long createNew(Account account);
}
