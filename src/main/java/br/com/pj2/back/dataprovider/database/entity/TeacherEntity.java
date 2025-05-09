package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.domain.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
//    private Set<DisciplineEntity> disciplines = new HashSet<>();
}