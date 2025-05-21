package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringScheduleResponse {
    private Long id;
    private String monitor;
    private String discipline;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static MonitoringScheduleResponse of(MonitoringScheduleDomain domain) {
        return MonitoringScheduleResponse.builder()
                .id(domain.getId())
                .monitor(domain.getMonitor())
                .discipline(domain.getMonitoring())
                .dayOfWeek(domain.getDayOfWeek().name())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .build();
    }
}
