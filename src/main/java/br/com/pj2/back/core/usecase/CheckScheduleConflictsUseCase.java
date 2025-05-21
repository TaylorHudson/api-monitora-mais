package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.exception.ConflictException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.entrypoint.api.dto.MonitoringScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CheckScheduleConflictsUseCase {
    private final MonitoringScheduleGateway scheduleGateway;
    private final MonitoringGateway disciplineGateway;

    public void execute(MonitoringScheduleRequest request) throws BindException {
        var dayOfWeek = parseDayOfWeek(request.getDayOfWeek());
        MonitoringDomain discipline = disciplineGateway.findByName(request.getMonitoring());

        if (discipline.getAllowMonitorsSameTime()) {
            return;
        }

        boolean conflictExists = scheduleGateway.existsByDisciplineNameAndDayOfWeekAndTimeRangeAndStatusIn(
                discipline.getName(),
                dayOfWeek,
                request.getStartTime(),
                request.getEndTime(),
                List.of(MonitoringScheduleStatus.PENDING, MonitoringScheduleStatus.APPROVED)
        );

        if (conflictExists) {
            throw new ConflictException(ErrorCode.MONITORING_SCHEDULE_REQUEST_CONFLICT);
        }
    }

    private DayOfWeek parseDayOfWeek(String dayOfWeek) throws BindException {
        try {
            return DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validValues = String.join(", ", Stream.of(DayOfWeek.values())
                    .map(Enum::name)
                    .toList());
            throw new BindException(dayOfWeek, "Valores válidos para o dia da semana são: " + validValues);
        }
    }
}
