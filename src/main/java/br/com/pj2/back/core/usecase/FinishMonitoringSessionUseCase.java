package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinishMonitoringSessionUseCase {
    private final MonitoringSessionGateway sessionGateway;
    private final MonitoringScheduleGateway scheduleGateway;

    public void execute(Long monitoringScheduleId, String description, String registration) {
        var schedule = scheduleGateway.findByIdAndMonitorRegistration(monitoringScheduleId, registration);
        var session = sessionGateway.findByMonitorAndIsStartedTrue(schedule.getMonitor());
        session.finishSession(description);
        sessionGateway.save(session);
    }
}
