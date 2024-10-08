package com.jwapp.financialapi.application.core.usecase.user;

import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.ports.CreateEntityPort;
import com.jwapp.financialapi.application.ports.gateway.UserGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserImpl implements CreateEntityPort<User> {

    private final UserGatewayPort userGatewayPort;

    @Override
    public Long createNew(User user) {
        User userDb = userGatewayPort.save(user);
        return userDb.getId();
    }
}
