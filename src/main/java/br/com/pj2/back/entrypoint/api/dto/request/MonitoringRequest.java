package br.com.pj2.back.entrypoint.api.dto.request;

import br.com.pj2.back.core.domain.MonitoringDomain;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MonitoringRequest {

    @NotEmpty(message = "É obrigatório informar o nome da monitoria")
    private String name;

    @NotNull(message = "É obrigatório informar se é permitido mas de um monitor no mesmo horário")
    private Boolean allowMonitorsSameTime;

    @NotEmpty(message = "A lista de tópicos não pode estar vazia")
    @Size(min = 1, message = "A lista deve ter pelo menos um tópico")
    private List<@NotBlank(message = "Nenhum tópico pode estar em branco") String> topics;

    public static MonitoringRequest of(MonitoringDomain domain){
        return MonitoringRequest.builder()
                .name(domain.getName())
                .allowMonitorsSameTime(domain.getAllowMonitorsSameTime())
                .build();
    }
}
