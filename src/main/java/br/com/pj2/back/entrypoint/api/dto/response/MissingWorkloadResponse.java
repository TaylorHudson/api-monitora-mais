package br.com.pj2.back.entrypoint.api.dto.response;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissingWorkloadResponse {
    private LocalTime missingWeeklyWorkload;
}
