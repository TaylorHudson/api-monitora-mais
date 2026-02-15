package br.com.pj2.back.entrypoint.api.dto.response;

import br.com.pj2.back.core.domain.MonitoringDomainDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringDetailsResponse {

    private Long id;
    private String name;
    private String teacher;
    private List<StudentMonitoringResponse> students;

    public static MonitoringDetailsResponse of(MonitoringDomainDetail domain){
        return MonitoringDetailsResponse.builder()
                .id(domain.getId())
                .name(domain.getName())
                .teacher(domain.getTeacher())
                .students(domain.getStudents() == null ? new ArrayList<>() : StudentMonitoringResponse.of(domain.getStudents()))
                .build();
    }
}
