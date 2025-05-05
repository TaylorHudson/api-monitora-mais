package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.UserDomain;
import br.com.pj2.back.core.domain.enumerated.Role;

public interface UserGateway {
    UserDomain findByRegistration(String registration);
    UserDomain findByRegistrationAndRole(String registration, Role role);
    UserDomain save(UserDomain domain);
}
