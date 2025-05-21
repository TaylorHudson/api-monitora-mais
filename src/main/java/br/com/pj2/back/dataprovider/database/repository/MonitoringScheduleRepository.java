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
    List<MonitoringScheduleEntity> findByMonitorRegistrationAndDayOfWeekAndStatus(String registration, DayOfWeek dayOfWeek, MonitoringScheduleStatus status);
    Optional<MonitoringScheduleEntity> findByIdAndMonitorRegistrationAndStatus(Long id, String registration, MonitoringScheduleStatus status);
    @Query("""
    SELECT CASE WHEN COUNT(ms) > 0 THEN true ELSE false END
    FROM MonitoringScheduleEntity ms
    WHERE ms.monitoring.name = :monitoringName
      AND ms.dayOfWeek = :dayOfWeek
      AND ms.startTime < :endTime
      AND ms.endTime > :startTime
      AND ms.status IN :statuses""")
    boolean existsByDisciplineNameAndDayOfWeekAndTimeRangeAndStatusIn(
            @Param("monitoringName") String monitoringName,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statuses") List<MonitoringScheduleStatus> statuses
    );

}
