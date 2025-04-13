package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.dataprovider.database.entity.enumerated.DaysWeek;
import br.com.pj2.back.dataprovider.database.entity.enumerated.StatusTimeRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "time_requests")
public class TimeRequestEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "time_request_persistence_date")
    private LocalDateTime timeRequestPersistenceDate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTimeRequest status;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_week", nullable = false)
    private DaysWeek daysWeek;

    @ManyToOne
    @JoinColumn(name = "monitoring_id", nullable = false)
    private MonitoringEntity monitoring;

    @ManyToOne
    @JoinColumn(name = "monitor_id", nullable = false)
    private MonitorEntity monitor;
    @PrePersist
    public void prePersist() {
        final LocalDateTime current = LocalDateTime.now();
        timeRequestPersistenceDate = current;
    }
}
