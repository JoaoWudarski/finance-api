package com.jwapp.financialapi.application.core.usecase.user;

import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.core.exception.NotFoundException;
import com.jwapp.financialapi.application.ports.FindEntityPort;
import com.jwapp.financialapi.application.ports.gateway.UserGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserImpl implements FindEntityPort<User, Long> {

    private final UserGatewayPort userGatewayPort;

    @Override
    public User byId(Long id) {
        return userGatewayPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Nao existe conta com id " + id));
    }

    @Override
    public boolean exists(Long id) {
        return userGatewayPort.existsById(id);
    }
}
