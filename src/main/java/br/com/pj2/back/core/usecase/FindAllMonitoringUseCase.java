package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllMonitoringUseCase {

    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    public List<MonitoringDomain> execute(String authorizationHeader){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        return monitoringGateway.findAllByTeacherRegistration(registration);
    }
}
