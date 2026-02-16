package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.common.utils.TimeUtils;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.gateway.StudentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubtractMissingWorkloadUseCase {
    private final StudentGateway studentGateway;

    public void execute(MonitoringScheduleDomain schedule) {
        var durationInMinutes = TimeUtils.durationInMinutes(schedule.getStartTime(), schedule.getEndTime());
        var student = studentGateway.findByRegistration(schedule.getMonitorRegistration());
        student.subtractMissingWorkload(durationInMinutes);
        studentGateway.save(student);
    }
}
