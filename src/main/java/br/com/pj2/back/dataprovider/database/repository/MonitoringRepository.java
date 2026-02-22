package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
    Optional<MonitoringEntity> findByName(String name);
    @Query("SELECT m FROM MonitoringEntity m JOIN m.students s WHERE s.registration = :registration")
    List<MonitoringEntity> findAllByStudentRegistration(@Param("registration") String registration);

    @Query("SELECT m FROM MonitoringEntity m JOIN m.teacher s WHERE s.registration = :registration")
    List<MonitoringEntity> findAllByTeacherRegistration(@Param("registration") String registration);
    Optional<MonitoringEntity> findByIdAndTeacherRegistration(Long id, String registration);
}
