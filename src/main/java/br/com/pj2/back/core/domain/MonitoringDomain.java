package br.com.pj2.back.core.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringDomain {
    private Long id;
    private String name;
    private Boolean allowMonitorsSameTime;
    private String teacher;
    private List<String> students;
    private List<MonitoringScheduleDomain> schedules;
    private List<MonitoringSessionDomain> sessions;
}