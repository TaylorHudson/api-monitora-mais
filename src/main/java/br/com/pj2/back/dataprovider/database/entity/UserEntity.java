package br.com.pj2.back.dataprovider.database.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "registration", unique = true, nullable = false)
    private String registration;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "user_persistence_date")
    private LocalDateTime userPersistenceDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MonitorEntity monitor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TeacherEntity teacher;
    public enum UserType{
        ROLE_ADMIN, ROLE_USER;
    }
    @PrePersist
    public void prePersist() {
        final LocalDateTime current = LocalDateTime.now();
        userPersistenceDate = current;
    }

}
