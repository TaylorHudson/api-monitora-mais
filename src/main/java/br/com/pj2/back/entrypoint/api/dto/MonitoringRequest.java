package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.MonitoringDomain;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringRequest {

    @NotEmpty(message = "É obrigatório informar o nome da monitoria")
    private String name;

    @NotEmpty(message = "É obrigatório informar se é permitido mas de um monitor no mesmo horário")
    private Boolean allowMonitorsSameTime;

    @NotEmpty(message = "É obrigatório informar o professor responsável da monitoria")
    private String teacher;

    public static MonitoringRequest of(MonitoringDomain domain){
        return MonitoringRequest.builder()
                .name(domain.getName())
                .teacher(domain.getTeacher())
                .allowMonitorsSameTime(domain.getAllowMonitorsSameTime())
                .build();
    }
}
