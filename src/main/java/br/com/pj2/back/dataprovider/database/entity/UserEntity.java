package br.com.pj2.back.dataprovider.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String registration;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;
}