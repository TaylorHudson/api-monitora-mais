package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "points")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startMonitoring;
    @Column(nullable = false)
    private LocalDateTime endMonitoring;
    @Column
    private String description;

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

    public void setStartMonitoring(LocalDateTime startMonitoring) {
        this.startMonitoring = startMonitoring;
    }

    public void setEndMonitoring(LocalDateTime endMonitoring) {
        this.endMonitoring = endMonitoring;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }
}