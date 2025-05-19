package br.com.pj2.back.entrypoint.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishMonitoringSessionRequest {
    @NotNull(message = "O id da monitoria agendada é obrigatório.")
    private Long monitoringScheduleId;
    private String description;
}
