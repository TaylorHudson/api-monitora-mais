package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monitors")
public class MonitorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
    private Set<PointEntity> points = new HashSet<>();

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
    private Set<MonitoringTimeEntity> monitoringTimes = new HashSet<>();

    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
    private Set<TimeRequestEntity> timeRequests = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "monitor_monitoring",
            joinColumns = @JoinColumn(name = "monitor_id"),
            inverseJoinColumns = @JoinColumn(name = "monitoring_id")
    )
    private Set<MonitoringEntity> monitorings = new HashSet<>();
}
