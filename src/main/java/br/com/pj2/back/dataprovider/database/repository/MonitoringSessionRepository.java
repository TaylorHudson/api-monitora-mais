package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MonitoringSessionRepository extends JpaRepository<MonitoringSessionEntity, Long> {
    Optional<MonitoringSessionEntity> findByMonitorRegistrationAndIsStartedTrue(String monitorRegistration);
}
