package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ConflictException;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StartMonitoringSessionUseCase {
    private final MonitoringSessionGateway sessionGateway;
    private final MonitoringScheduleGateway scheduleGateway;

    public void execute(Long monitoringScheduleId, String registration) {
        var schedule = scheduleGateway.findByIdAndMonitorRegistration(monitoringScheduleId, registration);

        checkIfSessionAlreadyStarted(schedule.getMonitor());
        validateSessionStartTime(schedule);

        var session = new MonitoringSessionDomain(schedule.getMonitor(), schedule.getMonitoring());
        session.startSession();
        sessionGateway.save(session);
    }

    private void validateSessionStartTime(MonitoringScheduleDomain schedule) {
        var now = LocalTime.now();
        if (now.isBefore(schedule.getStartTime()) || now.isAfter(schedule.getStartTime().plusMinutes(5))) {
            throw new ConflictException(ErrorCode.MONITORING_SESSION_CANNOT_BE_STARTED);
        }
    }

    private void checkIfSessionAlreadyStarted(String registration) {
        try {
            var session = sessionGateway.findByMonitorAndIsStartedTrue(registration);
            if (session != null) {
                throw new ConflictException(ErrorCode.MONITORING_SESSION_ALREADY_STARTED);
            }
        } catch (ResourceNotFoundException ignored) {}
    }
}
