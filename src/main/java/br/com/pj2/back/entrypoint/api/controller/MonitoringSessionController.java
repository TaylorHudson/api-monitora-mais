package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.FindStartedMonitoringSessionUseCase;
import br.com.pj2.back.core.usecase.FinishMonitoringSessionUseCase;
import br.com.pj2.back.core.usecase.StartMonitoringSessionUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.FinishMonitoringSessionRequest;
import br.com.pj2.back.entrypoint.api.dto.request.StartMonitoringSessionRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringSessionStartedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sessão de Monitoria", description = "Gerenciamento de sessões de monitoria")
@RestController
@RequestMapping("/monitoring/sessions")
@RequiredArgsConstructor
public class MonitoringSessionController {
     private final TokenGateway tokenGateway;
     private final StartMonitoringSessionUseCase startSessionUseCase;
     private final FinishMonitoringSessionUseCase finishSessionUseCase;
     private final FindStartedMonitoringSessionUseCase findStartedMonitoringSessionUseCase;

    @Operation(summary = "Buscar a sessão de monitoria iniciada")
    @GetMapping("/started")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringSessionStartedResponse findStartedSession(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        return findStartedMonitoringSessionUseCase.execute(registration);
    }

    @Operation(summary = "Iniciar uma sessão de monitoria")
    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public void startSession(
            @RequestBody @Valid StartMonitoringSessionRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        startSessionUseCase.execute(request.getMonitoringScheduleId(), registration);
    }

    @Operation(summary = "Finalizar uma sessão de monitoria")
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
