package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPendingSchedulesUseCase {
    private final TokenGateway tokenGateway;
    private final MonitoringScheduleGateway monitoringScheduleGateway;

    public List<MonitoringScheduleDomain> execute(String authorizationHeader) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        return monitoringScheduleGateway.findByTeacherRegistrationAndStatus(registration, MonitoringScheduleStatus.PENDING);
    }
}
