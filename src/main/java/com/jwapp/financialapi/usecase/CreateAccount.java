package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.controller.dto.request.AccountRequest;

public interface CreateAccount {

    Long createNew(AccountRequest accountRequest);
}
