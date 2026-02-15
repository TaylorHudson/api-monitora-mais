package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomainDetail;
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
class FindMonitoringDetailsByIdUseCaseTest {
    @Mock
    private MonitoringGateway monitoringGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private FindMonitoringDetailsByIdUseCase findMonitoringDetailsByIdUseCase;

    @Test
    void shouldReturnMonitoringDetailsWhenIdAndAuthorizationAreValid() {
        // Arrange
        Long id = 1L;
        String authHeader = "Bearer token";
        String registration = "teacher123";

        MonitoringDomainDetail expected = MonitoringDomainDetail.builder()
                .id(id)
                .name("Monitoring Detail")
                .teacher("Teacher Name")
                .build();

        when(tokenGateway.extractSubjectFromAuthorization(authHeader))
                .thenReturn(registration);

        when(monitoringGateway.findByIdDetails(id, registration))
                .thenReturn(expected);

        // Act
        MonitoringDomainDetail result =
                findMonitoringDetailsByIdUseCase.execute(id, authHeader);

        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
        assertEquals(id, result.getId());
        assertEquals("Monitoring Detail", result.getName());
        assertEquals("Teacher Name", result.getTeacher());
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsInvalid() {
        // Arrange
        Long id = 1L;
        String invalidHeader = "Invalid token";

        when(tokenGateway.extractSubjectFromAuthorization(invalidHeader))
                .thenThrow(new IllegalArgumentException("Invalid token"));

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> findMonitoringDetailsByIdUseCase.execute(id, invalidHeader)
        );
    }

}