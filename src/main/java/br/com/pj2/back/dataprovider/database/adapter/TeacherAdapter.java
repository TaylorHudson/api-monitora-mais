package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.TeacherGateway;
import br.com.pj2.back.dataprovider.database.entity.TeacherEntity;
import br.com.pj2.back.dataprovider.database.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherAdapter implements TeacherGateway {
    private final TeacherRepository teacherRepository;

    @Override
    public TeacherDomain findByRegistration(String registration) {
        return teacherRepository.findByRegistration(registration)
                .map(TeacherAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public TeacherDomain findByRegistrationAndRole(String registration, Role role) {
        return teacherRepository.findByRegistrationAndRole(registration, role)
                .map(TeacherAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public TeacherDomain save(TeacherDomain domain) {
        var entity = teacherRepository.save(toEntity(domain));
        return toDomain(entity);
    }

    @Override
    public TeacherDomain findByName(String name) {
        return teacherRepository.findByName(name)
                .map(TeacherAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private static TeacherDomain toDomain(TeacherEntity entity) {
        return TeacherDomain.builder()
                .registration(entity.getRegistration())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }

    public static TeacherEntity toEntity(TeacherDomain domain) {
        return TeacherEntity.builder()
                .registration(domain.getRegistration())
                .name(domain.getName())
                .email(domain.getEmail())
                .build();
    }
}