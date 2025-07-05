package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMonitoringByIdUseCase {

    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    public MonitoringDomain execute(Long id, String authorization){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorization);
        return monitoringGateway.findById(id, registration);
    }

}
