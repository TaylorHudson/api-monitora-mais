package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindMissingWorkloadUseCaseTest {
    @Mock
    private StudentGateway studentGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private FindMissingWorkloadUseCase findMissingWorkloadUseCase;

    @Test
    void shouldReturnMissingWorkloadAsLocalTimeForValidAuthorization() {
        String authHeader = "Bearer token";
        String registration = "student123";
        int missingMinutes = 90;

        var studentDomain = mock(StudentDomain.class);
        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(studentGateway.findByRegistration(registration)).thenReturn(studentDomain);
        when(studentDomain.getMissingWeeklyWorkload()).thenReturn(missingMinutes);

        LocalTime result = findMissingWorkloadUseCase.execute(authHeader);

        assertEquals(LocalTime.of(1, 30), result);
    }

    @Test
    void shouldReturnZeroWhenStudentHasNoMissingWorkload() {
        String authHeader = "Bearer token";
        String registration = "student123";
        int missingMinutes = 0;

        var studentDomain = mock(StudentDomain.class);
        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(studentGateway.findByRegistration(registration)).thenReturn(studentDomain);
        when(studentDomain.getMissingWeeklyWorkload()).thenReturn(missingMinutes);

        LocalTime result = findMissingWorkloadUseCase.execute(authHeader);

        assertEquals(LocalTime.of(0, 0), result);
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        String invalidHeader = "Invalid";
        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader)).thenThrow(new IllegalArgumentException("Invalid token"));

        assertThrows(IllegalArgumentException.class, () -> findMissingWorkloadUseCase.execute(invalidHeader));
        verify(studentGateway, never()).findByRegistration(any());
    }
}
