package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.dataprovider.database.entity.enumerated.StatusMonitoring;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.monitor.Monitor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monitorings")
public class MonitoringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_monitoring", nullable = false)
    private StatusMonitoring statusMonitoring;

    @Column(name = "days_week", nullable = false)
    private String daysWeek;

    @Column(name = "monitoring_persistence_date")
    private LocalDateTime monitoringPersistenceDate;

    @ManyToMany(mappedBy = "monitorings")
    private Set<Monitor> monitors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherEntity teacher;

    @OneToOne
    @JoinColumn(name = "discipline_id", nullable = false)
    private DisciplineEntity discipline;

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL )
    private Set<TimeRequestEntity> timeRequests = new HashSet<>();
    @PrePersist
    public void prePersist() {
        final LocalDateTime current = LocalDateTime.now();
        monitoringPersistenceDate = current;
    }
}
