package br.com.pj2.back.core.domain;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisciplineDomain {
    private Long id;
    private String name;
    private Boolean allowMonitorsSameTime;
    private TeacherDomain teacher;
    private Set<MonitoringScheduleDomain> schedules;
    //private Set<MonitoringSession> sessions;
}