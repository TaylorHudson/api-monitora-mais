package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.entity.UserEntity;
import br.com.pj2.back.dataprovider.database.repository.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringAdapter implements MonitoringGateway {
    private final MonitoringRepository monitoringRepository;
    private final TeacherAdapter teacherAdapter;
    private final StudentAdapter studentAdapter;

    @Override
    public MonitoringDomain findByName(String name) {
        return monitoringRepository.findByName(name)
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));
    }

    @Override
    public MonitoringDomain create(MonitoringDomain domain) {
        return toDomain(monitoringRepository.save(toEntity(domain)));
    }

    @Override
    public List<MonitoringDomain> findAllByStudentRegistration(String registration) {
        return monitoringRepository.findAllByStudentRegistration(registration)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    public MonitoringEntity toEntity(MonitoringDomain domain){
        List<StudentEntity> students = Collections.emptyList();
        if (domain.getStudents() != null && !domain.getStudents().isEmpty()) {
            students = domain.getStudents().stream()
                    .map(registration -> StudentAdapter.toEntity(studentAdapter.findByRegistration(registration)))
                    .toList();
        }

        return MonitoringEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .teacher(TeacherAdapter.toEntity(teacherAdapter.findByRegistration(domain.getTeacher())))
                .allowMonitorsSameTime(domain.getAllowMonitorsSameTime())
                .students(students)
                .build();
    }

    private MonitoringDomain toDomain(MonitoringEntity entity) {
        return MonitoringDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .allowMonitorsSameTime(entity.getAllowMonitorsSameTime())
                .teacher(entity.getTeacher().getRegistration())
                .students(entity.getStudents().stream().map(UserEntity::getRegistration).toList())
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
