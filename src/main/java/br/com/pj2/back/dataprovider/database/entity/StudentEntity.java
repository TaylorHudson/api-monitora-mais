package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.domain.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("STUDENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StudentEntity extends UserEntity {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
//    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
//    private Set<PointEntity> points = new HashSet<>();
//    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
//    private Set<MonitoringTimeEntity> monitoringTimes = new HashSet<>();
//    @OneToMany(mappedBy = "monitor", cascade = CascadeType.ALL )
//    private Set<TimeRequestEntity> timeRequests = new HashSet<>();
//    @ManyToMany
//    @JoinTable(
//            name = "monitor_monitoring",
//            joinColumns = @JoinColumn(name = "monitor_id"),
//            inverseJoinColumns = @JoinColumn(name = "monitoring_id")
//    )
//    private Set<MonitoringEntity> monitorings = new HashSet<>();
}