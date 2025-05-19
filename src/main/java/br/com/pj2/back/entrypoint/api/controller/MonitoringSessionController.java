package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.FinishMonitoringSessionUseCase;
import br.com.pj2.back.core.usecase.StartMonitoringSessionUseCase;
import br.com.pj2.back.entrypoint.api.dto.FinishMonitoringSessionRequest;
import br.com.pj2.back.entrypoint.api.dto.StartMonitoringSessionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitoring/sessions")
@RequiredArgsConstructor
public class MonitoringSessionController {
    private final TokenGateway tokenGateway;
    private final StartMonitoringSessionUseCase startSessionUseCase;
    private final FinishMonitoringSessionUseCase finishSessionUseCase;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public void startSession(
            @RequestBody @Valid StartMonitoringSessionRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        startSessionUseCase.execute(request.getMonitoringScheduleId(), registration);
    }

    @PostMapping("/finish")
    @ResponseStatus(HttpStatus.CREATED)
    public void finishSession(
            @RequestBody @Valid FinishMonitoringSessionRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        finishSessionUseCase.execute(request.getMonitoringScheduleId(), request.getDescription(), registration);
    }

}
