package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ConflictException;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
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
        try {
            var session = sessionGateway.findByMonitorAndIsStartedTrue(schedule.getMonitor());
            session.finishSession(description);
            sessionGateway.save(session);
        } catch (ResourceNotFoundException e) {
            throw new ConflictException(ErrorCode.MONITORING_SESSION_CANNOT_BE_FINISHED);
        }
    }
}
