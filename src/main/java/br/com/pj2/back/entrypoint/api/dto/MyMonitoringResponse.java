package br.com.pj2.back.entrypoint.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyMonitoringResponse {
    private Long id;
    private String name;
    private String teacher;
    private boolean alreadyRequested;

}
