package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TeacherGateway;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.entity.TeacherEntity;
import br.com.pj2.back.dataprovider.database.repository.StudentRepository;
import br.com.pj2.back.dataprovider.database.repository.TeacherRepository;
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
                .build();
    }

    public static StudentEntity toEntity(StudentDomain domain) {
        return StudentEntity.builder()
                .registration(domain.getRegistration())
                .name(domain.getName())
                .email(domain.getEmail())
                .build();
    }
}