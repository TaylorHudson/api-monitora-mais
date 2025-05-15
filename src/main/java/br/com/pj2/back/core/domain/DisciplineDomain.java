package br.com.pj2.back.core.domain;

import lombok.*;

import java.util.List;
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
    private String teacher;
    private List<MonitoringScheduleDomain> schedules;
    //private Set<MonitoringSession> sessions;
}