package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "monitoring")
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

    @ManyToMany
    @JoinTable(
            name = "monitoring_students",
            joinColumns = @JoinColumn(name = "monitoring_id"),
            inverseJoinColumns = @JoinColumn(name = "student_registration")
    )
    private List<StudentEntity> students;

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonitoringScheduleEntity> schedules = List.of();

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonitoringSessionEntity> sessions = List.of();
}