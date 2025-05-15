package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "monitoring_schedules")
public class MonitoringScheduleEntity {

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

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MonitoringScheduleStatus status = MonitoringScheduleStatus.PENDING;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime requestedAt = LocalDateTime.now();
}
