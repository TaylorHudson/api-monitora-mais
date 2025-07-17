package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringSessionStartedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindStartedMonitoringSessionUseCaseTest {
    @Mock
    private MonitoringSessionGateway sessionGateway;
    @InjectMocks
    private FindStartedMonitoringSessionUseCase findStartedMonitoringSessionUseCase;

    @Test
    void shouldReturnStartedSessionResponseForValidMonitorRegistration() {
        String registration = "monitor123";
        var session = mock(MonitoringSessionDomain.class);

        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(session);
        when(session.getStartTime()).thenReturn(LocalDateTime.of(2024, 6, 1, 10, 0));
        when(session.getMonitoring()).thenReturn("Math");
        when(session.getMonitoringSchedule()).thenReturn(42L);

        MonitoringSessionStartedResponse result = findStartedMonitoringSessionUseCase.execute(registration);

        assertEquals(LocalDateTime.of(2024, 6, 1, 10, 0), result.getStartTime());
        assertEquals("Math", result.getMonitoring());
        assertEquals(42L, result.getMonitoringScheduleId());
    }

    @Test
    void shouldThrowExceptionWhenNoStartedSessionExistsForMonitor() {
        String registration = "monitor123";
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> findStartedMonitoringSessionUseCase.execute(registration));
    }
}
