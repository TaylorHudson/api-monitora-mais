package br.com.pj2.back.entrypoint.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointResponse {
    private Long id;
    private LocalDateTime startMonitoring;
    private LocalDateTime endMonitoring;
    private String description;
}