package br.com.pj2.back.entrypoint.api.dto.request;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringScheduleRequest {
    @NotEmpty(message = "O nome da monitoria é obrigatório.")
    private String monitoring;
    @NotEmpty(message = "O dia da semana é obrigatório.")
    private String dayOfWeek;
    @NotNull(message = "A hora de início da monitoria é obrigatória.")
    private LocalTime startTime;
    @NotNull(message = "A hora de fim da monitoria é obrigatória.")
    private LocalTime endTime;

    public static MonitoringScheduleRequest of(MonitoringScheduleDomain domain) {
        return MonitoringScheduleRequest.builder()
                .monitoring(domain.getMonitoring())
                .dayOfWeek(domain.getDayOfWeek().name())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .build();
    }
}
