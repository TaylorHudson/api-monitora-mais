package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringSessionDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringSessionGateway;
import br.com.pj2.back.dataprovider.database.entity.DisciplineEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringSessionEntity;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.repository.DisciplineRepository;
import br.com.pj2.back.dataprovider.database.repository.MonitoringSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringSessionAdapter implements MonitoringSessionGateway {
    private final MonitoringSessionRepository monitoringSessionRepository;
    private final DisciplineRepository disciplineRepository;

    @Override
    public MonitoringSessionDomain save(MonitoringSessionDomain domain) {
        var discipline = disciplineRepository.findByName(domain.getDiscipline())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DISCIPLINE_NOT_FOUND));
        var entity = monitoringSessionRepository.save(
                MonitoringSessionEntity.builder()
                        .id(domain.getId())
                        .monitor(StudentEntity.builder().registration(domain.getMonitor()).build())
                        .discipline(DisciplineEntity.builder().id(discipline.getId()).build())
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
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_SESSION_NOT_FOUND));
        return toDomain(entity);
    }

    private MonitoringSessionDomain toDomain(MonitoringSessionEntity entity) {
        return MonitoringSessionDomain.builder()
                .id(entity.getId())
                .monitor(entity.getMonitor().getRegistration())
                .discipline(entity.getDiscipline().getName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .description(entity.getDescription())
                .isStarted(entity.isStarted())
                .build();
    }
}
