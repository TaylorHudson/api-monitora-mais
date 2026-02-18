package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomainDetail;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindMonitoringDetailsUseCase {
    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    public List<MonitoringDomainDetail> execute(String authorization){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorization);
        return monitoringGateway.findDetails(registration);
    }

}
