package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.MonitoringDomainDetail;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.StudentMonitoringDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.exception.BadRequestException;
import br.com.pj2.back.core.exception.ForbiddenException;
import br.com.pj2.back.core.exception.ResourceNotFoundException;
import br.com.pj2.back.core.exception.UnprocessableEntityException;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.dataprovider.database.entity.MonitoringEntity;
import br.com.pj2.back.dataprovider.database.entity.MonitoringScheduleEntity;
import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import br.com.pj2.back.dataprovider.database.entity.TeacherEntity;
import br.com.pj2.back.dataprovider.database.entity.UserEntity;
import br.com.pj2.back.dataprovider.database.repository.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringAdapter implements MonitoringGateway {
    private final MonitoringRepository monitoringRepository;
    private final TeacherAdapter teacherAdapter;
    private final StudentAdapter studentAdapter;
    private final MonitoringSessionAdapter monitoringSessionAdapter;

    @Override
    public MonitoringDomain findByName(String name) {
        return monitoringRepository.findByName(name)
                .map(this::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));
    }

    @Override
    public MonitoringDomain create(MonitoringDomain domain) {
        try {
            var entity = toEntity(domain);
            return toDomain(monitoringRepository.save(entity));
        } catch (DataIntegrityViolationException e) {
            throw new UnprocessableEntityException(ErrorCode.MONITORING_ALREADY_EXISTS);
        }
    }

    @Override
    public List<MonitoringDomain> findAllByStudentRegistration(String registration) {
        return monitoringRepository.findAllByStudentRegistration(registration)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<MonitoringDomain> findAllByTeacherRegistration(String registration) {
        List<MonitoringDomain> list = monitoringRepository.findAllByTeacherRegistration(registration)
                .stream()
                .map(this::toDomain).toList();
        if(list.isEmpty()){
            return list;
        }
        list.forEach(monitoring -> {
            monitoring.setCountTopicsInSession(monitoringSessionAdapter.countTopicsInSessionMonitoring(monitoring));
        });

        return list;
    }

    @Override
    public void deleteById(Long id, String registration){
        var monitoring = monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));

        if(!monitoring.getTeacher().getRegistration().equalsIgnoreCase(registration)){
            throw new ForbiddenException(ErrorCode.DO_NOT_HAVE_PERMISSION_TO_DELETE_THIS_MONITORING);
        }
        monitoringRepository.deleteById(id);
    }

    @Override
    public MonitoringDomain findById(Long id, String registration) {
        var monitoring = monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));
        if(monitoring.getTeacher().getRegistration().equalsIgnoreCase(registration)){
            return toDomain(monitoring);
        }
        throw new ForbiddenException(ErrorCode.DO_NOT_HAVE_PERMISSION_TO_ACCESS_THE_MONITORING);
    }

    @Override
    public MonitoringDomainDetail findByIdDetails(Long id, String registration) {
        var monitoring = monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));
        if(monitoring.getTeacher().getRegistration().equalsIgnoreCase(registration)){
            return toDomainDetail(monitoring);
        }
        throw new ForbiddenException(ErrorCode.DO_NOT_HAVE_PERMISSION_TO_ACCESS_THE_MONITORING);
    }

    @Override
    public List<MonitoringDomainDetail> findDetails(String registration) {
        return monitoringRepository.findAllByTeacherRegistration(registration)
                .stream()
                .map(this::toDomainDetail)
                .toList();
    }

    @Override
    public MonitoringDomain update(Long id, MonitoringDomain domain, String registration) {
        MonitoringEntity entity = monitoringRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorCode.MONITORING_NOT_FOUND));

        if(entity.getTeacher().getRegistration().equalsIgnoreCase(registration)){

            if ((domain.getTeacher() == null || domain.getTeacher().isBlank()) &&
                            domain.getAllowMonitorsSameTime() == null &&
                            (domain.getTopics() == null || domain.getTopics().isEmpty()) &&
                            (domain.getName() == null || domain.getName().isBlank())) {
                throw new BadRequestException(ErrorCode.VALIDATION_ERROR);
            }

            String topics = convertListToString(domain.getTopics());
            entity = buildMonitoringEntityUpdate(domain, entity,topics);
            return toDomain(monitoringRepository.save(entity));
        }
        throw new ForbiddenException(ErrorCode.DO_NOT_HAVE_TO_UPDATE_THIS_MONITORING);
    }

    private MonitoringEntity buildMonitoringEntityUpdate(MonitoringDomain domain, MonitoringEntity entity, String topics) {
        TeacherEntity updatedTeacher = getUpdatedString(domain.getTeacher(), null) != null
                ? TeacherAdapter.toEntity(teacherAdapter.findByRegistration(domain.getTeacher()))
                : entity.getTeacher();

        return MonitoringEntity.builder()
                .id(entity.getId())
                .name(getUpdatedString(domain.getName(), entity.getName()))
                .allowMonitorsSameTime(getUpdatedValue(domain.getAllowMonitorsSameTime(), entity.getAllowMonitorsSameTime()))
                .teacher(updatedTeacher)
                .students(entity.getStudents())
                .sessions(entity.getSessions())
                .schedules(entity.getSchedules())
                .topics(getUpdatedString(topics, entity.getTopics()))
                .build();
    }

    private static String getUpdatedString(String newValue, String currentValue) {
        return newValue != null && !newValue.isBlank() ? newValue : currentValue;
    }

    private static <T> T getUpdatedValue(T newValue, T currentValue) {
        return newValue != null ? newValue : currentValue;
    }

    public static String convertListToString(List<String> list) {
        if(list != null && !list.isEmpty()){
            return String.join(",", list);
        }
        return null;
    }

    public static List<String> convertStringToList(String topics) {
        return topics == null || topics.isBlank()
                ? Collections.emptyList()
                : Arrays.asList(topics.split("\\s*,\\s*"));
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
                .schedules(new ArrayList<>())
                .topics(convertListToString(domain.getTopics()))
                .build();
    }

    private MonitoringDomain toDomain(MonitoringEntity entity) {
        return MonitoringDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .allowMonitorsSameTime(entity.getAllowMonitorsSameTime())
                .teacher(entity.getTeacher().getRegistration())
                .students(entity.getStudents().stream().map(UserEntity::getRegistration).toList())
                .schedules(entity.getSchedules() == null ? new ArrayList<>() : entity.getSchedules().stream().map(this::toScheduleDomain).toList())
                .topics(convertStringToList(entity.getTopics()))
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
                .status(entity.getStatus())
                .requestedAt(entity.getRequestedAt())
                .build();
    }

    private MonitoringDomainDetail toDomainDetail(MonitoringEntity entity) {

        List<StudentMonitoringDomain> studentMonitoringDomains = new ArrayList<>();

        for (StudentEntity studentEntity : entity.getStudents()) {
            var student = StudentMonitoringDomain.of(studentEntity);
            studentMonitoringDomains.add(student);
        }

        for (MonitoringScheduleEntity schedule : entity.getSchedules()) {
            studentMonitoringDomains.stream()
                    .filter(student -> student.getRegistration()
                            .equals(schedule.getMonitor().getRegistration()))
                    .findFirst()
                    .ifPresent(student -> {
                                if (MonitoringScheduleStatus.APPROVED.equals(schedule.getStatus())) {
                                    student.getDaysOfWeek().add(String.valueOf(schedule.getDayOfWeek()));
                                }
                            }
                    );
        }
        return MonitoringDomainDetail.builder()
                .id(entity.getId())
                .name(entity.getName())
                .teacher(entity.getTeacher().getName())
                .students(studentMonitoringDomains)
                .build();
    }
}
