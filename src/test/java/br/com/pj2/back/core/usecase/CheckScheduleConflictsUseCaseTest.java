package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.exception.ConflictException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringScheduleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckScheduleConflictsUseCaseTest {
    @Mock
    private MonitoringScheduleGateway scheduleGateway;
    @Mock
    private MonitoringGateway monitoringGateway;
    @InjectMocks
    private CheckScheduleConflictsUseCase checkScheduleConflictsUseCase;

    private MonitoringScheduleRequest request;
    private MonitoringDomain monitoringDomain;

    @BeforeEach
    void setUp() {
        request = new MonitoringScheduleRequest();
        request.setDayOfWeek("MONDAY");
        request.setStartTime(LocalTime.parse("08:00"));
        request.setEndTime(LocalTime.parse("10:00"));
        request.setMonitoring("EDA");

        monitoringDomain = MonitoringDomain.builder()
                .allowMonitorsSameTime(false)
                .build();
    }

    @Test
    void shouldPassWhenNoConflictExists() {
        when(monitoringGateway.findByName(request.getMonitoring())).thenReturn(monitoringDomain);
        when(scheduleGateway.existsByDayOfWeekAndTimeRangeAndStatusIn(any(), any(), any(), any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> checkScheduleConflictsUseCase.execute(request));
    }

    @Test
    void shouldThrowConflictExceptionWhenConflictExists() {
        when(monitoringGateway.findByName(request.getMonitoring())).thenReturn(monitoringDomain);
        when(scheduleGateway.existsByDayOfWeekAndTimeRangeAndStatusIn(any(), any(), any(), any(), any())).thenReturn(true);

        assertThrows(ConflictException.class, () -> checkScheduleConflictsUseCase.execute(request));
    }

    @Test
    void shouldPassWhenMonitoringAllowsMonitorsSameTime() {
        monitoringDomain.setAllowMonitorsSameTime(true);
        when(monitoringGateway.findByName(request.getMonitoring())).thenReturn(monitoringDomain);

        assertDoesNotThrow(() -> checkScheduleConflictsUseCase.execute(request));
        verifyNoInteractions(scheduleGateway);
    }

    @Test
    void shouldThrowBindExceptionForInvalidDayOfWeek() {
        request.setDayOfWeek("INVALID_DAY");

        assertThrows(BindException.class, () -> checkScheduleConflictsUseCase.execute(request));
    }
}
