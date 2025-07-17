package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindSchedulesByFilterUseCaseTest {
    @Mock
    private TokenGateway tokenGateway;
    @Mock
    private MonitoringScheduleGateway monitoringScheduleGateway;
    @InjectMocks
    private FindSchedulesByFilterUseCase findSchedulesByFilterUseCase;

    @Test
    void shouldReturnSchedulesForTeacherWithGivenStatus() {
        String authHeader = "Bearer token";
        String registration = "teacher123";
        String status = "PENDING";
        List<MonitoringScheduleDomain> expectedSchedules = List.of(
                MonitoringScheduleDomain.builder().id(1L).status(MonitoringScheduleStatus.PENDING).build(),
                MonitoringScheduleDomain.builder().id(2L).status(MonitoringScheduleStatus.PENDING).build()
        );

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(monitoringScheduleGateway.findByTeacherRegistrationAndStatus(registration, MonitoringScheduleStatus.PENDING)).thenReturn(expectedSchedules);

        List<MonitoringScheduleDomain> result = findSchedulesByFilterUseCase.execute(status, authHeader);

        assertEquals(expectedSchedules, result);
    }

    @Test
    void shouldReturnEmptyListWhenNoSchedulesMatchStatus() {
        String authHeader = "Bearer token";
        String registration = "teacher123";
        String status = "APPROVED";

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(monitoringScheduleGateway.findByTeacherRegistrationAndStatus(registration, MonitoringScheduleStatus.APPROVED)).thenReturn(List.of());

        List<MonitoringScheduleDomain> result = findSchedulesByFilterUseCase.execute(status, authHeader);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        String invalidHeader = "Invalid";
        String status = "PENDING";
        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader)).thenThrow(new IllegalArgumentException("Invalid token"));

        assertThrows(IllegalArgumentException.class, () -> findSchedulesByFilterUseCase.execute(status, invalidHeader));
    }

}
