package br.com.pj2.back.entrypoint.api.dto.response;

import br.com.pj2.back.core.domain.StudentMonitoringDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentMonitoringResponse {

    private String name;
    private String registration;
    private List<String> daysOfWeek = new ArrayList<>();

    public static List<StudentMonitoringResponse> of(List<StudentMonitoringDomain> domain) {
        List<StudentMonitoringResponse> response = new ArrayList<>();

        domain.forEach(d -> {
            response.add(StudentMonitoringResponse.builder()
                    .name(d.getName())
                    .registration(d.getRegistration())
                    .daysOfWeek(d.getDaysOfWeek() == null ? new ArrayList<>() : d.getDaysOfWeek().stream()
                            .sorted(Comparator.comparing(DayOfWeek::valueOf))
                            .toList())
                    .build());
        });
        return response;
    }
}