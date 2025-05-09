package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.dataprovider.database.entity.UserEntity;
import br.com.pj2.back.dataprovider.database.repository.UserRepository;
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
class UserAdapterTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserAdapter userAdapter;

    private final String registration = "123456";
    private final Role role = Role.STUDENT;
    private final UserEntity userEntity = new UserEntity(registration, role);

    @Test
    void shouldFindUserByRegistration() {
        when(userRepository.findByRegistration(registration)).thenReturn(Optional.of(userEntity));

        UserDomain result = userAdapter.findByRegistration(registration);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(userRepository).findByRegistration(registration);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByRegistration() {
        when(userRepository.findByRegistration(registration)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAdapter.findByRegistration(registration));
    }

    @Test
    void shouldFindUserByRegistrationAndRole() {
        when(userRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.of(userEntity));

        UserDomain result = userAdapter.findByRegistrationAndRole(registration, role);

        assertEquals(registration, result.getRegistration());
        assertEquals(role, result.getRole());
        verify(userRepository).findByRegistrationAndRole(registration, role);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByRegistrationAndRole() {
        when(userRepository.findByRegistrationAndRole(registration, role)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userAdapter.findByRegistrationAndRole(registration, role));
    }

    @Test
    void shouldSaveUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDomain domain = UserDomain.builder().registration(registration).role(role).build();
        UserDomain saved = userAdapter.save(domain);

        assertEquals(registration, saved.getRegistration());
        assertEquals(role, saved.getRole());
        verify(userRepository).save(any(UserEntity.class));
    }
}