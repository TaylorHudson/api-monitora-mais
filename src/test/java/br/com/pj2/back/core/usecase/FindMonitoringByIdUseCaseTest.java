package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindMonitoringByIdUseCaseTest {
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private FindMonitoringByIdUseCase findMonitoringByIdUseCase;

    @Test
    void shouldReturnMonitoringDomainWhenIdAndAuthorizationAreValid() {
        Long id = 1L;
        String authHeader = "Bearer token";
        String registration = "teacher123";
        MonitoringDomain expectedMonitoring = MonitoringDomain.builder().id(id).teacher(registration).build();

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);
        when(monitoringGateway.findById(id, registration)).thenReturn(expectedMonitoring);

        MonitoringDomain result = findMonitoringByIdUseCase.execute(id, authHeader);

        assertEquals(expectedMonitoring, result);
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        Long id = 1L;
        String invalidHeader = "Invalid";
        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader)).thenThrow(new IllegalArgumentException("Invalid token"));

        assertThrows(IllegalArgumentException.class, () -> findMonitoringByIdUseCase.execute(id, invalidHeader));
    }

}
