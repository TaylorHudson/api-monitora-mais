package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllMonitoringUseCaseTest {
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private FindAllMonitoringUseCase findAllMonitoringUseCase;

    @Test
    void shouldReturnAllMonitoringForTeacher() {
        String authHeader = "Bearer token";
        String registration = "teacher123";
        List<MonitoringDomain> expectedMonitoring = List.of(
                MonitoringDomain.builder().name("Math").teacher(registration).build(),
                MonitoringDomain.builder().name("Physics").teacher(registration).build()
        );

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(monitoringGateway.findAllByTeacherRegistration(registration)).thenReturn(expectedMonitoring);

        List<MonitoringDomain> result = findAllMonitoringUseCase.execute(authHeader);

        assertEquals(expectedMonitoring, result);
    }

    @Test
    void shouldReturnEmptyListWhenTeacherHasNoMonitoring() {
        String authHeader = "Bearer token";
        String registration = "teacher123";

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(monitoringGateway.findAllByTeacherRegistration(registration)).thenReturn(List.of());

        List<MonitoringDomain> result = findAllMonitoringUseCase.execute(authHeader);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        String invalidHeader = "Invalid";
        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader)).thenThrow(new IllegalArgumentException("Invalid token"));

        assertThrows(IllegalArgumentException.class, () -> findAllMonitoringUseCase.execute(invalidHeader));
        verifyNoInteractions(monitoringGateway);
    }
}
