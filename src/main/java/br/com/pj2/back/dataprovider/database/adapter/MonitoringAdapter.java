package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.repository.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitoringAdapter implements MonitoringGateway {
    private final MonitoringRepository monitoringRepository;
    protected final TeacherAdapter teacherAdapter;

    @Override
    public MonitoringDomain findByName(String name) {
        return monitoringRepository.findByName(name)
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DISCIPLINE_NOT_FOUND));
    }

    @Override
    public MonitoringDomain create(MonitoringDomain domain) {
        return toDomain(monitoringRepository.save(toEntity(domain)));
    }

    public MonitoringEntity toEntity(MonitoringDomain domain){
        return MonitoringEntity.builder()
                .name(domain.getName())
                .teacher(TeacherAdapter.toEntity(teacherAdapter.findByRegistration(domain.getTeacher())))
                .allowMonitorsSameTime(domain.getAllowMonitorsSameTime())
                .build();
    }

    private MonitoringDomain toDomain(MonitoringEntity entity) {
        return MonitoringDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .allowMonitorsSameTime(entity.getAllowMonitorsSameTime())
                .teacher(entity.getTeacher().getRegistration())
                .schedules(entity.getSchedules().stream().map(this::toScheduleDomain).toList())
                .build();
    }

    private MonitoringScheduleDomain toScheduleDomain(MonitoringScheduleEntity entity) {
        return MonitoringScheduleDomain.builder()
                .id(entity.getId())
                .monitor(entity.getMonitor().getRegistration())
                .monitoring(entity.getMonitoring().getName())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
