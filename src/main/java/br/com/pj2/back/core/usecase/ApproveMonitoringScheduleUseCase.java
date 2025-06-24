package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ForbiddenException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveMonitoringScheduleUseCase {
    private final TokenGateway tokenGateway;
    private final MonitoringScheduleGateway monitoringScheduleGateway;
    private final MonitoringGateway monitoringGateway;

    public void execute(String authorizationHeader, Long scheduleId) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        var schedule = monitoringScheduleGateway.findById(scheduleId);
        var monitoring = monitoringGateway.findByName(schedule.getMonitoring());

        if (!monitoring.getTeacher().equals(registration)) {
            throw new ForbiddenException(ErrorCode.DO_NOT_HAVE_PERMISSION_TO_APPROVE);
        }

        schedule.approve();
        monitoringScheduleGateway.save(schedule);
    }
}
