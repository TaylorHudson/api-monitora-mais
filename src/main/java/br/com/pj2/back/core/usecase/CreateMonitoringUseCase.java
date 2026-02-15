package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateMonitoringUseCase {

    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    public MonitoringDomain execute(MonitoringRequest request, String authorizationHeader){
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        log.info("[CreateMonitoringUseCase] Creating monitoring | registration=[{}]", registration);
        return monitoringGateway.create(
                MonitoringDomain.builder()
                        .name(request.getName())
                        .allowMonitorsSameTime(request.getAllowMonitorsSameTime())
                        .teacher(registration)
                        .schedules(List.of())
                        .sessions(List.of())
                        .topics(request.getTopics())
                        .build());
    }
}
