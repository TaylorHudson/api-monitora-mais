package br.com.pj2.back.entrypoint.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitoringSessionStartedResponse {
    private LocalDateTime startTime;
    private String monitoring;
    private Long monitoringScheduleId;
}
