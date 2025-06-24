package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringSessionEntity;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.repository.MonitoringRepository;
import br.com.pj2.back.dataprovider.database.repository.MonitoringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringSessionAdapter implements MonitoringSessionGateway {
    private final MonitoringSessionRepository monitoringSessionRepository;
    private final MonitoringRepository monitoringRepository;

    @Override
    public MonitoringSessionDomain save(MonitoringSessionDomain domain) {
        var discipline = monitoringRepository.findByName(domain.getMonitoring())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));
        var entity = monitoringSessionRepository.save(
                MonitoringSessionEntity.builder()
                        .id(domain.getId())
                        .monitor(StudentEntity.builder().registration(domain.getMonitor()).build())
                        .monitoring(MonitoringEntity.builder().id(discipline.getId()).build())
                        .monitoringSchedule(MonitoringScheduleEntity.builder().id(domain.getMonitoringSchedule()).build())
                        .startTime(domain.getStartTime())
                        .endTime(domain.getEndTime())
                        .description(domain.getDescription())
                        .isStarted(domain.isStarted())
                        .build()
        );
        return toDomain(entity);
    }

    @Override
    public MonitoringSessionDomain findByMonitorAndIsStartedTrue(String monitorRegistration) {
        var entity = monitoringSessionRepository.findByMonitorRegistrationAndIsStartedTrue(monitorRegistration)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NO_STARTED_MONITORING_SESSION_WAS_FOUND));
        return toDomain(entity);
    }

    private MonitoringSessionDomain toDomain(MonitoringSessionEntity entity) {
        return MonitoringSessionDomain.builder()
                .id(entity.getId())
                .monitor(entity.getMonitor().getRegistration())
                .monitoring(entity.getMonitoring().getName())
                .monitoringSchedule(entity.getMonitoringSchedule().getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .description(entity.getDescription())
                .isStarted(entity.isStarted())
                .build();
    }
}
