package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ConflictException;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartMonitoringSessionUseCaseTest {
    @Mock
    private MonitoringSessionGateway sessionGateway;
    @Mock
    private MonitoringScheduleGateway scheduleGateway;
    @InjectMocks
    private StartMonitoringSessionUseCase startMonitoringSessionUseCase;

    @Test
    void shouldStartSessionSuccessfullyWhenNoSessionExistsAndTimeIsValid() {
        Long scheduleId = 1L;
        String registration = "monitor123";
        var schedule = mock(MonitoringScheduleDomain.class);

        when(scheduleGateway.findByIdAndMonitorRegistration(scheduleId, registration)).thenReturn(schedule);
        when(schedule.getMonitor()).thenReturn(registration);
        when(schedule.getMonitoring()).thenReturn("Math");
        when(schedule.getStartTime()).thenReturn(LocalTime.now());
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(null);

        startMonitoringSessionUseCase.execute(scheduleId, registration);

        verify(sessionGateway).save(any(MonitoringSessionDomain.class));
    }

    @Test
    void shouldThrowConflictExceptionWhenSessionAlreadyStarted() {
        Long scheduleId = 1L;
        String registration = "monitor123";
        var schedule = mock(MonitoringScheduleDomain.class);
        var existingSession = mock(MonitoringSessionDomain.class);

        when(scheduleGateway.findByIdAndMonitorRegistration(scheduleId, registration)).thenReturn(schedule);
        when(schedule.getMonitor()).thenReturn(registration);
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(existingSession);

        assertThrows(ConflictException.class, () -> startMonitoringSessionUseCase.execute(scheduleId, registration));
    }

    @Test
    void shouldThrowConflictExceptionWhenStartTimeIsBeforeAllowedWindow() {
        Long scheduleId = 1L;
        String registration = "monitor123";
        var schedule = mock(MonitoringScheduleDomain.class);

        when(scheduleGateway.findByIdAndMonitorRegistration(scheduleId, registration)).thenReturn(schedule);
        when(schedule.getMonitor()).thenReturn(registration);
        when(schedule.getStartTime()).thenReturn(LocalTime.now().plusMinutes(10));
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(null);

        assertThrows(ConflictException.class, () -> startMonitoringSessionUseCase.execute(scheduleId, registration));
        verify(sessionGateway, never()).save(any());
    }

    @Test
    void shouldThrowConflictExceptionWhenStartTimeIsAfterAllowedWindow() {
        Long scheduleId = 1L;
        String registration = "monitor123";
        var schedule = mock(MonitoringScheduleDomain.class);

        when(scheduleGateway.findByIdAndMonitorRegistration(scheduleId, registration)).thenReturn(schedule);
        when(schedule.getMonitor()).thenReturn(registration);
        when(schedule.getStartTime()).thenReturn(LocalTime.now().minusMinutes(10));
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenReturn(null);

        assertThrows(ConflictException.class, () -> startMonitoringSessionUseCase.execute(scheduleId, registration));
        verify(sessionGateway, never()).save(any());
    }

    @Test
    void shouldStartSessionWhenResourceNotFoundExceptionIsThrownBySessionGateway() {
        Long scheduleId = 1L;
        String registration = "monitor123";
        var schedule = mock(MonitoringScheduleDomain.class);

        when(scheduleGateway.findByIdAndMonitorRegistration(scheduleId, registration)).thenReturn(schedule);
        when(schedule.getMonitor()).thenReturn(registration);
        when(schedule.getMonitoring()).thenReturn("Math");
        when(schedule.getStartTime()).thenReturn(LocalTime.now());
        when(sessionGateway.findByMonitorAndIsStartedTrue(registration)).thenThrow(new ResourceNotFoundException(ErrorCode.NO_STARTED_MONITORING_SESSION_WAS_FOUND));

        startMonitoringSessionUseCase.execute(scheduleId, registration);

        verify(sessionGateway).save(any(MonitoringSessionDomain.class));
    }
}
