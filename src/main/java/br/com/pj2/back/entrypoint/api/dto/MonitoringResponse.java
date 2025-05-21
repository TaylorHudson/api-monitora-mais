package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
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
public class MonitoringResponse {

    private Long id;
    private String name;
    private Boolean allowMonitorsSameTime;
    private String teacher;
    private List<MonitoringScheduleDomain> schedules;

    public static MonitoringResponse of(MonitoringDomain domain){
        return MonitoringResponse.builder()
                .id(domain.getId())
                .teacher(domain.getTeacher())
                .allowMonitorsSameTime(domain.getAllowMonitorsSameTime())
                .name(domain.getName())
                .schedules(domain.getSchedules())
                .build();
    }
}
