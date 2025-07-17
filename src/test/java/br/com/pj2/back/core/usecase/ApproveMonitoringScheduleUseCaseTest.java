package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.exception.ForbiddenException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveMonitoringScheduleUseCaseTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private MonitoringScheduleGateway monitoringScheduleGateway;
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private SubtractMissingWorkloadUseCase subtractMissingWorkloadUseCase;
    @InjectMocks
    private ApproveMonitoringScheduleUseCase approveMonitoringScheduleUseCase;

    private MonitoringScheduleDomain monitoringScheduleDomain;
    private MonitoringDomain monitoringDomain;

    @BeforeEach
    void setUp() {
        monitoringScheduleDomain = MonitoringScheduleDomain.builder()
                .id(1L)
                .status(MonitoringScheduleStatus.PENDING)
                .monitoring("EDA")
                .build();

        monitoringDomain = MonitoringDomain.builder()
                .teacher("teacherRegistration")
                .build();
    }

    @Test
    void shouldApproveWhenTeacherHasPermission() {
        var teacherRegistration = "teacherRegistration";
        var scheduleId = 1L;
        var authHeader = "Bearer token";

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(teacherRegistration);
        when(monitoringScheduleGateway.findById(scheduleId)).thenReturn(monitoringScheduleDomain);
        when(monitoringGateway.findByName(monitoringScheduleDomain.getMonitoring())).thenReturn(monitoringDomain);

        approveMonitoringScheduleUseCase.execute(authHeader, scheduleId);

        assertEquals(MonitoringScheduleStatus.APPROVED, monitoringScheduleDomain.getStatus());

        verify(subtractMissingWorkloadUseCase, times(1)).execute(monitoringScheduleDomain);
        verify(monitoringScheduleGateway, times(1)).save(monitoringScheduleDomain);
    }

    @Test
    void shouldNotApproveWhenTeacherHasNoPermission() {
        var teacherRegistration = "differentTeacherRegistration";
        var scheduleId = 1L;
        var authHeader = "Bearer token";

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(teacherRegistration);
        when(monitoringScheduleGateway.findById(scheduleId)).thenReturn(monitoringScheduleDomain);
        when(monitoringGateway.findByName(monitoringScheduleDomain.getMonitoring())).thenReturn(monitoringDomain);

        assertThrows(ForbiddenException.class, () -> approveMonitoringScheduleUseCase.execute(authHeader, scheduleId));
        assertEquals(MonitoringScheduleStatus.PENDING, monitoringScheduleDomain.getStatus());

        verifyNoInteractions(subtractMissingWorkloadUseCase);
    }

}
