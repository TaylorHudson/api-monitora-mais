package br.com.pj2.back.entrypoint.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class PointRequest {
    @NotNull(message = "A data e hora de início da monitoria é obrigatória")
    private LocalDateTime startMonitoring;
    @NotNull(message = "A data e hora de fim da monitoria é obrigatória")
    private LocalDateTime endMonitoring;
    private String description;

    public PointRequest() {
    }

    public PointRequest(LocalDateTime startMonitoring, LocalDateTime endMonitoring, String description) {
        this.startMonitoring = startMonitoring;
        this.endMonitoring = endMonitoring;
        this.description = description;
    }

    public LocalDateTime getStartMonitoring() {
        return startMonitoring;
    }

    public LocalDateTime getEndMonitoring() {
        return endMonitoring;
    }

    public String getDescription() {
        return description;
    }
}