package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface MonitoringScheduleGateway {
    List<MonitoringScheduleDomain> findByTeacherRegistrationAndStatus(String registration, MonitoringScheduleStatus status);
    List<MonitoringScheduleDomain> findByMonitorRegistrationAndDayOfWeek(String registration, DayOfWeek dayOfWeek);
    MonitoringScheduleDomain findByIdAndMonitorRegistration(Long id, String registration);
    MonitoringScheduleDomain findById(Long id);
    MonitoringScheduleDomain save(MonitoringScheduleDomain domain);
    boolean existsByDayOfWeekAndTimeRangeAndStatusIn(Long monitoringId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, List<MonitoringScheduleStatus> statuses);
}
