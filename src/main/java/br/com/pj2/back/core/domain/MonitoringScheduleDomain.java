package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringScheduleDomain {
    private Long id;
    private String monitorRegistration;
    private String monitor;
    private String monitoring;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private MonitoringScheduleStatus status;
    private LocalDateTime requestedAt;

    public boolean approve() {
        if (this.status == MonitoringScheduleStatus.PENDING) {
            this.status = MonitoringScheduleStatus.APPROVED;
            return true;
        }
        return false;
    }

    public boolean deny() {
        if (this.status == MonitoringScheduleStatus.PENDING) {
            this.status = MonitoringScheduleStatus.DENIED;
            return true;
        }
        return false;
    }
}
