package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "disciplines")
public class DisciplineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "allow_monitors_same_time", nullable = false)
    private Boolean allowMonitorsSameTime;

    @ManyToOne(optional = false)
    private TeacherEntity teacher;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitoringScheduleEntity> schedules = new HashSet<>();

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitoringSession> sessions = new HashSet<>();
}