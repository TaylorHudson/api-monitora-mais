package br.com.pj2.back.entrypoint.api.dto.response;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringScheduleResponse {
    private Long id;
    private String monitorRegistration;
    private String monitor;
    private String discipline;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private List<String> topics;

    public static MonitoringScheduleResponse of(MonitoringScheduleDomain domain, List<String> topics) {
        return MonitoringScheduleResponse.builder()
                .id(domain.getId())
                .monitorRegistration(domain.getMonitorRegistration())
                .monitor(domain.getMonitor())
                .discipline(domain.getMonitoring())
                .dayOfWeek(domain.getDayOfWeek().name())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .status(domain.getStatus().name())
                .topics(topics.isEmpty() ? List.of() : topics)
                .build();
    }

    public static MonitoringScheduleResponse of(MonitoringScheduleDomain domain) {
        return MonitoringScheduleResponse.of(domain, List.of());
    }
}
