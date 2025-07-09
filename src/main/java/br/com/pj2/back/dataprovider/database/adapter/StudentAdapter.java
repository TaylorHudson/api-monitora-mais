package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAdapter implements StudentGateway {
    private final StudentRepository studentRepository;

    @Override
    public StudentDomain findByRegistration(String registration) {
        return studentRepository.findByRegistration(registration)
                .map(StudentAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public StudentDomain findByRegistrationAndRole(String registration, Role role) {
        return studentRepository.findByRegistrationAndRole(registration, role)
                .map(StudentAdapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public StudentDomain save(StudentDomain domain) {
        var entity = studentRepository.save(toEntity(domain));
        return toDomain(entity);
    }

    private static StudentDomain toDomain(StudentEntity entity) {
        return StudentDomain.builder()
                .registration(entity.getRegistration())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .weeklyWorkload(entity.getWeeklyWorkload())
                .missingWeeklyWorkload(entity.getMissingWeeklyWorkload())
                .build();
    }

    public static StudentEntity toEntity(StudentDomain domain) {
        return StudentEntity.builder()
                .registration(domain.getRegistration())
                .name(domain.getName())
                .email(domain.getEmail())
                .weeklyWorkload(domain.getWeeklyWorkload())
                .missingWeeklyWorkload(domain.getMissingWeeklyWorkload())
                .build();
    }
}