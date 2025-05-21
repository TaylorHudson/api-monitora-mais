package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.domain.enumerated.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("TEACHER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TeacherEntity extends UserEntity {
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.TEACHER;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<MonitoringEntity> monitorings = new HashSet<>();
}