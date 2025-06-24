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
    private String monitor;
    private String monitoring;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private MonitoringScheduleStatus status;
    private LocalDateTime requestedAt;

    public void approve() {
        this.status = MonitoringScheduleStatus.APPROVED;
    }

    public void deny() {
        this.status = MonitoringScheduleStatus.DENIED;
    }
}