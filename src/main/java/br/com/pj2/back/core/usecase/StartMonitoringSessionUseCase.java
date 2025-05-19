package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartMonitoringSessionUseCase {
    private final MonitoringSessionGateway sessionGateway;
    private final MonitoringScheduleGateway scheduleGateway;

    public void execute(Long monitoringScheduleId, String registration) {
        var schedule = scheduleGateway.findByIdAndMonitorRegistration(monitoringScheduleId, registration);

        checkIfSessionAlreadyStarted(schedule.getMonitor());

        var session = new MonitoringSessionDomain(schedule.getMonitor(), schedule.getDiscipline());
        session.startSession();
        sessionGateway.save(session);
    }

    private void checkIfSessionAlreadyStarted(String registration) {
        try {
            var session = sessionGateway.findByMonitorAndIsStartedTrue(registration);
            if (session != null) {
                throw new IllegalStateException("A sessão de monitoria já foi iniciada.");
            }
        } catch (ResourceNotFoundException ignored) {}
    }
}
