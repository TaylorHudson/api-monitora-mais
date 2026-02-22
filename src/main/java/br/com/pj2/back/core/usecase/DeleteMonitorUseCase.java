package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMonitorUseCase {

    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;
    public void execute(Long monitoringId, String studentRegistration, String authorization){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorization);
        monitoringGateway.deleteStudent(monitoringId,studentRegistration, registration);
    }
}
