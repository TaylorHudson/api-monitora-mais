package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface MonitoringScheduleRepository extends JpaRepository<MonitoringScheduleEntity, Long> {
    List<MonitoringScheduleEntity> findByMonitorRegistrationAndDayOfWeekAndStatus(String registration, DayOfWeek dayOfWeek, MonitoringScheduleStatus status);
    Optional<MonitoringScheduleEntity> findByIdAndMonitorRegistrationAndStatus(Long id, String registration, MonitoringScheduleStatus status);
}
