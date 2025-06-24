package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.dataprovider.database.entity.MonitoringSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoringSessionRepository extends JpaRepository<MonitoringSessionEntity, Long> {
    Optional<MonitoringSessionEntity> findByMonitorRegistrationAndIsStartedTrue(String monitorRegistration);
}
