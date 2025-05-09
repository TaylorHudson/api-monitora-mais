package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.dataprovider.database.entity.TeacherEntity;
import br.com.pj2.back.dataprovider.database.repository.TeacherRepository;
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
class TeacherAdapterTest {
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherAdapter teacherAdapter;

    private final String registration = "1234567";
    private final Role role = Role.TEACHER;
    private final TeacherEntity teacherEntity = TeacherEntity.builder().registration(registration).role(role).build();

    @Test
    void shouldFindTeacherByRegistration() {
        when(teacherRepository.findByRegistration(registration)).thenReturn(Optional.of(teacherEntity));

        TeacherDomain result = teacherAdapter.findByRegistration(registration);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(teacherRepository).findByRegistration(registration);
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFoundByRegistration() {
        when(teacherRepository.findByRegistration(registration)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teacherAdapter.findByRegistration(registration));
    }

    @Test
    void shouldFindTeacherByRegistrationAndRole() {
        when(teacherRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.of(teacherEntity));

        TeacherDomain result = teacherAdapter.findByRegistrationAndRole(registration, role);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(teacherRepository).findByRegistrationAndRole(registration, role);
    }

    @Test
    void shouldThrowExceptionWhenTeacherNotFoundByRegistrationAndRole() {
        when(teacherRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teacherAdapter.findByRegistrationAndRole(registration, role));
    }

    @Test
    void shouldSaveTeacher() {
        TeacherDomain domain = TeacherDomain.builder()
                .registration(registration)
                .role(role)
                .build();

        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(teacherEntity);

        TeacherDomain saved = teacherAdapter.save(domain);

        assertEquals(registration, saved.getRegistration());
        assertEquals(role, saved.getRole());
        verify(teacherRepository).save(any(TeacherEntity.class));
    }
}