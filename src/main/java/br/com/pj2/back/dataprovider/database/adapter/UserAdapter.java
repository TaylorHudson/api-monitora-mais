package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.UserDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.UserGateway;
import br.com.pj2.back.dataprovider.database.entity.UserEntity;
import br.com.pj2.back.dataprovider.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserGateway {
    private final UserRepository userRepository;

    @Override
    public UserDomain findByRegistration(String registration) {
        return userRepository.findByRegistration(registration)
                .map(UserAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDomain findByRegistrationAndRole(String registration, Role role) {
        return userRepository.findByRegistrationAndRole(registration, role)
                .map(UserAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserDomain save(UserDomain domain) {
        var entity = userRepository.save(toEntity(domain));
        return toDomain(entity);
    }

    private static UserDomain toDomain(UserEntity entity) {
        return UserDomain.builder()
                .registration(entity.getRegistration())
                .role(entity.getRole())
                .build();
    }

    private static UserEntity toEntity(UserDomain domain) {
        return UserEntity.builder()
                .registration(domain.getRegistration())
                .role(domain.getRole())
                .build();
    }
}