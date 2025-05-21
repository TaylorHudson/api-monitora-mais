package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "disciplines")
public class MonitoringEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "allow_monitors_same_time", nullable = false)
    private Boolean allowMonitorsSameTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_registration", referencedColumnName = "registration")
    private TeacherEntity teacher;

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitoringScheduleEntity> schedules = new HashSet<>();

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitoringSessionEntity> sessions = new HashSet<>();
}