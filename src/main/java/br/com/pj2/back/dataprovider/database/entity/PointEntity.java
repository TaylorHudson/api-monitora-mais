package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.dataprovider.database.entity.enumerated.DaysWeek;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "points")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_monitoring", nullable = false)
    private LocalDateTime startMonitoring;

    @Column(name = "end_monitoring",nullable = false)
    private LocalDateTime endMonitoring;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_week", nullable = false)
    private DaysWeek dayWeek;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false)
    private MonitorEntity monitor;

}
