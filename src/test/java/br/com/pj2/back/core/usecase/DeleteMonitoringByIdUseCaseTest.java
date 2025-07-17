package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteMonitoringByIdUseCaseTest {
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private DeleteMonitoringByIdUseCase deleteMonitoringByIdUseCase;

    @Test
    void shouldDeleteMonitoringWhenIdExists() {
        Long monitoringId = 1L;
        var registration = "registration";
        var authHeader = "Bearer token";

        when(tokenGateway.extractSubjectFromAuthorization(authHeader)).thenReturn(registration);

        assertDoesNotThrow(() -> deleteMonitoringByIdUseCase.execute(monitoringId, authHeader));
        verify(monitoringGateway).deleteById(monitoringId, registration);
    }

}
