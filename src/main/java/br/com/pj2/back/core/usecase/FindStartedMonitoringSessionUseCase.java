package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringSessionStartedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStartedMonitoringSessionUseCase {
    private final MonitoringSessionGateway sessionGateway;

    public MonitoringSessionStartedResponse execute(String registration) {
        var session = sessionGateway.findByMonitorAndIsStartedTrue(registration);
        return MonitoringSessionStartedResponse.builder()
                .startTime(session.getStartTime())
                .monitoring(session.getMonitoring())
                .monitoringScheduleId(session.getMonitoringSchedule())
                .build();
    }
}
