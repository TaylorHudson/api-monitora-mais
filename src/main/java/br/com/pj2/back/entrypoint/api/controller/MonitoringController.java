package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.entrypoint.api.dto.MonitoringRequest;
import br.com.pj2.back.entrypoint.api.dto.MonitoringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final TokenGateway tokenGateway;

    private final MonitoringGateway monitoringGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringResponse create(
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        MonitoringDomain domain = monitoringGateway.create(
                MonitoringDomain.builder()
                        .name(request.getName())
                        .allowMonitorsSameTime(request.getAllowMonitorsSameTime())
                        .teacher(registration)
                        .build());
        return MonitoringResponse.of(domain);
    }
}
