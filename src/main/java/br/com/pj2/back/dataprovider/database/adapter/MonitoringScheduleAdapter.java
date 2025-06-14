package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.repository.MonitoringRepository;
import br.com.pj2.back.dataprovider.database.repository.MonitoringScheduleRepository;
import br.com.pj2.back.dataprovider.database.repository.StudentRepository;
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
    private final MonitoringRepository monitoringRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<MonitoringScheduleDomain> findByTeacherRegistrationAndStatus(String registration, MonitoringScheduleStatus status) {
        var entities = monitoringScheduleRepository.findByTeacherRegistrationAndStatus(registration, status.name());
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

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
    public MonitoringScheduleDomain findById(Long id) {
        return monitoringScheduleRepository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_SCHEDULE_NOT_FOUND));
    }

    @Override
    @Transactional
    public MonitoringScheduleDomain save(MonitoringScheduleDomain domain) {
        var monitoring = monitoringRepository.findByName(domain.getMonitoring()).orElseThrow(
                ()-> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND)
        );

        var student = studentRepository.findById(domain.getMonitor())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        if (!monitoring.getStudents().contains(student)) {
            monitoring.getStudents().add(student);
            monitoringRepository.save(monitoring);
            // lançar exceção pois um estudante não pode ser monitor de uma monitoria que não está inscrito
        }

        var entity = monitoringScheduleRepository.save(
          MonitoringScheduleEntity.builder()
                  .id(domain.getId())
                  .monitor(student)
                  .monitoring(monitoring)
                  .dayOfWeek(domain.getDayOfWeek())
                  .startTime(domain.getStartTime())
                  .endTime(domain.getEndTime())
                  .status(domain.getStatus())
                  .requestedAt(domain.getRequestedAt())
                  .build()
        );
        return toDomain(entity);
    }

    @Override
    public boolean existsByDayOfWeekAndTimeRangeAndStatusIn(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, List<MonitoringScheduleStatus> statuses) {
        return monitoringScheduleRepository.existsByDayOfWeekAndTimeRangeAndStatusIn(dayOfWeek, startTime, endTime, statuses);
    }

    private MonitoringScheduleDomain toDomain(MonitoringScheduleEntity entity) {
        return MonitoringScheduleDomain.builder()
                .id(entity.getId())
                .monitor(entity.getMonitor().getRegistration())
                .monitoring(entity.getMonitoring().getName())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .status(entity.getStatus())
                .requestedAt(entity.getRequestedAt())
                .build();
    }
}
