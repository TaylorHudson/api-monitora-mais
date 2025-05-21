package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
    Optional<MonitoringEntity> findByName(String name);
}
