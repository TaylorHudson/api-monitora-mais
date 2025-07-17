package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMonitoringUseCaseTest {
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private CreateMonitoringUseCase createMonitoringUseCase;

    private MonitoringRequest request;

    @BeforeEach
    void setUp() {
        request = new MonitoringRequest();
        request.setName("Math");
        request.setAllowMonitorsSameTime(true);
        request.setTopics(List.of("Algebra", "Geometry"));
    }

    @Test
    void shouldCreateMonitoringWithValidRequest() {
        String authorizationHeader = "Bearer token";
        String registration = "teacher123";
        MonitoringDomain expectedDomain = MonitoringDomain.builder()
                .name(request.getName())
                .allowMonitorsSameTime(request.getAllowMonitorsSameTime())
                .teacher(registration)
                .topics(request.getTopics())
                .build();

        when(tokenGateway.extractSubjectFromAuthorization(authorizationHeader)).thenReturn(registration);
        when(monitoringGateway.create(any(MonitoringDomain.class))).thenReturn(expectedDomain);

        MonitoringDomain result = createMonitoringUseCase.execute(request, authorizationHeader);

        assertEquals(expectedDomain, result);
    }

    @Test
    void shouldPassEmptyTopicsListWhenRequestHasNoTopics() {
        String authorizationHeader = "Bearer token";
        String registration = "teacher123";
        request.setTopics(null);

        MonitoringDomain expectedDomain = MonitoringDomain.builder()
                .name(request.getName())
                .allowMonitorsSameTime(request.getAllowMonitorsSameTime())
                .teacher(registration)
                .topics(null)
                .build();

        when(tokenGateway.extractSubjectFromAuthorization(authorizationHeader)).thenReturn(registration);
        when(monitoringGateway.create(any(MonitoringDomain.class))).thenReturn(expectedDomain);

        MonitoringDomain result = createMonitoringUseCase.execute(request, authorizationHeader);

        assertEquals(expectedDomain, result);
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        String invalidHeader = "Invalid";
        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader)).thenThrow(new IllegalArgumentException("Invalid token"));

        assertThrows(IllegalArgumentException.class, () -> createMonitoringUseCase.execute(request, invalidHeader));
    }
}
