package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDomain {
    private String registration;
    private String name;
    private String email;
    private Role role;
//  private Set<PointDomain> points = new HashSet<>();
//  private Set<MonitoringTimeDomain> monitoringTimes = new HashSet<>();
//  private Set<TimeRequestDomain> timeRequests = new HashSet<>();
//  private Set<MonitoringDomain> monitorings = new HashSet<>();
}