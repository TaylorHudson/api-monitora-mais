package br.com.pj2.back.entrypoint.api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissingWorkloadResponse {
    private int missingWeeklyWorkload;
}
