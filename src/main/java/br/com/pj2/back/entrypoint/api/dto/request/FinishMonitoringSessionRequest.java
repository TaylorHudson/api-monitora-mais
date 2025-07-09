package br.com.pj2.back.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishMonitoringSessionRequest {
    @NotNull(message = "O id da monitoria agendada é obrigatório.")
    private Long monitoringScheduleId;
    private List<String> topics;
}
