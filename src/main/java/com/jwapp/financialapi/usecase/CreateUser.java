package com.jwapp.financialapi.usecase;

import com.jwapp.financialapi.domain.User;

public interface CreateUser {

    Long createNew(User user);
}
