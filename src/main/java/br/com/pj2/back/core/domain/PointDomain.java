package br.com.pj2.back.core.domain;

import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class PointDomain {
    private Long id;
    private LocalDateTime startMonitoring;
    private LocalDateTime endMonitoring;
    private String description;

    public PointDomain() {
    }

    public PointDomain(Long id, LocalDateTime startMonitoring, LocalDateTime endMonitoring, String description) {
        this.id = id;
        this.startMonitoring = startMonitoring;
        this.endMonitoring = endMonitoring;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartMonitoring() {
        return startMonitoring;
    }

    public void setStartMonitoring(LocalDateTime startMonitoring) {
        this.startMonitoring = startMonitoring;
    }

    public LocalDateTime getEndMonitoring() {
        return endMonitoring;
    }

    public void setEndMonitoring(LocalDateTime endMonitoring) {
        this.endMonitoring = endMonitoring;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}