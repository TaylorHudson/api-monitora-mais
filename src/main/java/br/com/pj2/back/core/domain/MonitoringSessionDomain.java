package br.com.pj2.back.core.domain;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoringSessionDomain {
    private Long id;
    private String monitor;
    private String monitoring;
    private Long monitoringSchedule;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private boolean isStarted;

    public MonitoringSessionDomain(String monitor, String monitoring, Long monitoringSchedule) {
        this.monitor = monitor;
        this.monitoring = monitoring;
        this.monitoringSchedule = monitoringSchedule;
    }

    public void startSession() {
        this.startTime = LocalDateTime.now();
        this.isStarted = true;
    }

    public void finishSession(String description) {
        this.endTime = LocalDateTime.now();
        this.isStarted = false;
        if (description == null || description.isBlank()) {
            this.description = "Nenhum comentário adicionado pelo monitor.";
        }
    }
}
