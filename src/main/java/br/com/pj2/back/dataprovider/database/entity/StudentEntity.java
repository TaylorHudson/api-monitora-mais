package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.common.constants.Constants;
import br.com.pj2.back.core.domain.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @ManyToMany(mappedBy = "students")
    private List<MonitoringEntity> monitorings;
    @Builder.Default
    private int weeklyWorkload = Constants.WEEKLY_WORKLOAD;
    @Builder.Default
    private int missingWeeklyWorkload = Constants.WEEKLY_WORKLOAD;
}