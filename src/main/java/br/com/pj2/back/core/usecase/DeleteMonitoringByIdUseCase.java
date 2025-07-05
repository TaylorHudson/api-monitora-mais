package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMonitoringByIdUseCase {

    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    public void execute(Long id, String authorizationHeader){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        monitoringGateway.deleteById(id, registration);
    }
}
