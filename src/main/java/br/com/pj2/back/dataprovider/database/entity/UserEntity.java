package br.com.pj2.back.dataprovider.database.entity;

import br.com.pj2.back.core.domain.enumerated.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String registration;
    @Enumerated(EnumType.STRING)
    private Role role;
}