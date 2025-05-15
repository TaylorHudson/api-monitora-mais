package br.com.pj2.back.dataprovider.database.repository;

import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringScheduleRepository extends JpaRepository<MonitoringScheduleEntity, Long> {
    List<MonitoringScheduleEntity> findByMonitorAndDayOfWeek(String monitor, String dayOfWeek);
}
