package br.com.pj2.back.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringDomainDetail {

    private Long id;
    private String name;
    private String teacher;
    private List<StudentMonitoringDomain> students;
}
