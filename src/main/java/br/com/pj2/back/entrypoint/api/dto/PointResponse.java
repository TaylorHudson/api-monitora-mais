package br.com.pj2.back.entrypoint.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointResponse {
    private Long id;
    private LocalDateTime startMonitoring;
    private LocalDateTime endMonitoring;
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartMonitoring(LocalDateTime startMonitoring) {
        this.startMonitoring = startMonitoring;
    }

    public void setEndMonitoring(LocalDateTime endMonitoring) {
        this.endMonitoring = endMonitoring;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
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