package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.dataprovider.database.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String> {
    Optional<TeacherEntity> findByRegistration(String registration);
    Optional<TeacherEntity> findByRegistrationAndRole(String registration, Role role);
}
