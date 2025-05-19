package br.com.pj2.back.core.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MonitoringSessionDomain {
    @Setter
    private Long id;
    @Setter
    private String monitor;
    @Setter
    private String discipline;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private boolean isStarted;

    public MonitoringSessionDomain(String monitor, String discipline) {
        this.monitor = monitor;
        this.discipline = discipline;
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
