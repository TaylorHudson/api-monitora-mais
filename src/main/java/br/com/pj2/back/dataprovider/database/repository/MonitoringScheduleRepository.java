package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MonitoringScheduleRepository extends JpaRepository<MonitoringScheduleEntity, Long> {
    List<MonitoringScheduleEntity> findByMonitorRegistrationAndStatus(String registration, MonitoringScheduleStatus status);
    Optional<MonitoringScheduleEntity> findByIdAndMonitorRegistrationAndStatus(Long id, String registration, MonitoringScheduleStatus status);
    @Query("""
    SELECT CASE WHEN COUNT(ms) > 0 THEN true ELSE false END
    FROM MonitoringScheduleEntity ms
    WHERE ms.dayOfWeek = :dayOfWeek
      AND ms.monitoring.id = :monitoringId
      AND ms.startTime < :endTime
      AND ms.endTime > :startTime
      AND ms.status IN :statuses""")
    boolean existsByDayOfWeekAndTimeRangeAndStatusIn(
            @Param("monitoringId") Long monitoringId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statuses") List<MonitoringScheduleStatus> statuses
    );

    @Query(value = """
        SELECT *
        FROM monitoring_schedules
        WHERE status = :status AND monitoring_id IN (
            SELECT id FROM monitoring WHERE teacher_registration = :registration
        )
    """, nativeQuery = true)
    List<MonitoringScheduleEntity> findByTeacherRegistrationAndStatus(
            @Param("registration") String registration,
            @Param("status") String status
    );
}
