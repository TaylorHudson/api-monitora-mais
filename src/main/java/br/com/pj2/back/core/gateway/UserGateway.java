package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.UserDomain;
import br.com.pj2.back.core.domain.enumerated.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    default Optional<UserDomain> findByRegistration(String registration) {
        return Optional.of(
                UserDomain
                        .builder()
                        .id(UUID.randomUUID().toString())
                        .registration(registration)
                        .roles(List.of(Role.STUDENT))
                        .build()
        );
    }
}
