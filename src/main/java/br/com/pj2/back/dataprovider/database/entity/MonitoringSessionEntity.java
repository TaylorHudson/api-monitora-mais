package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JoinColumn(name = "monitoring_id", referencedColumnName = "id")
    private MonitoringEntity monitoring;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_started", nullable = false)
    private boolean isStarted;
}
