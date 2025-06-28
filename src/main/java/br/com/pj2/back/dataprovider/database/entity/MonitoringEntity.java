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

    @Column(name = "topics", nullable = true)
    private String topics;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_registration", referencedColumnName = "registration")
    private TeacherEntity teacher;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_students",
            joinColumns = @JoinColumn(referencedColumnName = "id", name = "monitoring_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "registration", name = "student_registration")
    )
    private List<StudentEntity> students;

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MonitoringScheduleEntity> schedules;

    @OneToMany(mappedBy = "monitoring", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MonitoringSessionEntity> sessions;
}