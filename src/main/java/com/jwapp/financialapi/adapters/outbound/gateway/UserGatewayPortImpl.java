package com.jwapp.financialapi.adapters.outbound.gateway;

import com.jwapp.financialapi.adapters.outbound.repository.UserRepository;
import com.jwapp.financialapi.application.core.domain.User;
import com.jwapp.financialapi.application.ports.gateway.UserGatewayPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserGatewayPortImpl implements UserGatewayPort {

    private final UserRepository userRepository;

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
