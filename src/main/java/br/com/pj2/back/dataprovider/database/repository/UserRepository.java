package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.dataprovider.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByRegistration(String registration);
    Optional<UserEntity> findByRegistrationAndRole(String registration, Role role);
}
