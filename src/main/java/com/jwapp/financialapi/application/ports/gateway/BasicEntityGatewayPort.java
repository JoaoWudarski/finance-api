package com.jwapp.financialapi.application.ports.gateway;

import java.util.Optional;

public interface BasicEntityGatewayPort<T> {

    T save(T entity);
    Optional<T> findById(Long id);
}
