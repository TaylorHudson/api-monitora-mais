package br.com.pj2.back.entrypoint.api.dto.request;

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
public class MonitoringUpdateRequest {

    private String name;

    private Boolean allowMonitorsSameTime;

    private String teacher;

    private List<String> topics;
}
