package br.com.pj2.back.core.domain.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonitoringScheduleStatus {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    DENIED("Negado");

    private final String description;
}
