package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAdapterTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentAdapter studentAdapter;

    private final String registration = "765432142510";
    private final Role role = Role.STUDENT;
    private final StudentEntity studentEntity = StudentEntity.builder().registration(registration).role(role).build();

    @Test
    void shouldFindStudentByRegistration() {
        when(studentRepository.findByRegistration(registration)).thenReturn(Optional.of(studentEntity));

        StudentDomain result = studentAdapter.findByRegistration(registration);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(studentRepository).findByRegistration(registration);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFoundByRegistration() {
        when(studentRepository.findByRegistration(registration)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentAdapter.findByRegistration(registration));
    }

    @Test
    void shouldFindStudentByRegistrationAndRole() {
        when(studentRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.of(studentEntity));

        StudentDomain result = studentAdapter.findByRegistrationAndRole(registration, role);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(studentRepository).findByRegistrationAndRole(registration, role);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFoundByRegistrationAndRole() {
        when(studentRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentAdapter.findByRegistrationAndRole(registration, role));
    }

    @Test
    void shouldSaveStudent() {
        StudentDomain domain = StudentDomain.builder()
                .registration(registration)
                .role(role)
                .build();

        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

        StudentDomain saved = studentAdapter.save(domain);

        assertEquals(registration, saved.getRegistration());
        assertEquals(role, saved.getRole());
        verify(studentRepository).save(any(StudentEntity.class));
    }
}