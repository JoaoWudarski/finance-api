package com.jwapp.financialapi.application.ports.gateway;

import com.jwapp.financialapi.application.core.domain.User;

public interface UserGatewayPort extends BasicEntityGatewayPort<User> {

    Boolean existsById(Long id);
}
