package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monitoring_sessions")
public class MonitoringSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "monitor_registration", referencedColumnName = "registration")
    private StudentEntity monitor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_id", referencedColumnName = "id")
    private DisciplineEntity discipline;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_started", nullable = false)
    private boolean isStarted;
}
