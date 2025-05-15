package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.DisciplineDomain;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.DisciplineGateway;
import br.com.pj2.back.dataprovider.database.entity.DisciplineEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisciplineAdapter implements DisciplineGateway {
    private final DisciplineRepository disciplineRepository;

    @Override
    public DisciplineDomain findByName(String name) {
        return disciplineRepository.findByName(name)
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.DISCIPLINE_NOT_FOUND));
    }

    private DisciplineDomain toDomain(DisciplineEntity entity) {
        return DisciplineDomain.builder()
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
                .discipline(entity.getDiscipline().getName())
                .dayOfWeek(entity.getDayOfWeek())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}
