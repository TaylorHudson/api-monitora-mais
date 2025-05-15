package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.repository.DisciplineRepository;
import br.com.pj2.back.dataprovider.database.repository.MonitoringScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringScheduleAdapter implements MonitoringScheduleGateway {
    private final MonitoringScheduleRepository monitoringScheduleRepository;
    private final DisciplineRepository disciplineRepository;

    @Override
    public List<MonitoringScheduleDomain> findByMonitorRegistrationAndDayOfWeek(String registration, DayOfWeek dayOfWeek) {
        var entities = monitoringScheduleRepository.findByMonitorRegistrationAndDayOfWeekAndStatus(registration, dayOfWeek, MonitoringScheduleStatus.APPROVED);
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public MonitoringScheduleDomain findByIdAndMonitorRegistration(Long id, String registration) {
        var entity = monitoringScheduleRepository.findByIdAndMonitorRegistrationAndStatus(id, registration, MonitoringScheduleStatus.APPROVED);
        return entity
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_SCHEDULE_NOT_FOUND));
    }

    @Override
    @Transactional
    public MonitoringScheduleDomain create(MonitoringScheduleDomain domain) {
        var discipline = disciplineRepository.findByName(domain.getDiscipline());
        var entity = monitoringScheduleRepository.save(
          MonitoringScheduleEntity.builder()
                  .monitor(StudentEntity.builder().registration(domain.getMonitor()).build())
                  .discipline(discipline)
                  .dayOfWeek(domain.getDayOfWeek())
                  .startTime(domain.getStartTime())
                  .endTime(domain.getEndTime())
                  .build()
        );
        return toDomain(entity);
    }

    @Override
    public boolean existsByDisciplineNameAndDayOfWeekAndTimeRangeAndStatusIn(String disciplineName, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, List<MonitoringScheduleStatus> statuses) {
        return monitoringScheduleRepository.existsByDisciplineNameAndDayOfWeekAndTimeRangeAndStatusIn(disciplineName, dayOfWeek, startTime, endTime, statuses);
    }

    private MonitoringScheduleDomain toDomain(MonitoringScheduleEntity entity) {
        return MonitoringScheduleDomain.builder()
                .id(entity.getId())
                .monitor(entity.getMonitor().getRegistration())
                .discipline(entity.getDiscipline().getName())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
