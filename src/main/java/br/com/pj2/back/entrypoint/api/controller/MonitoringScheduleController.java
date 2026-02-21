package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.ApproveMonitoringScheduleUseCase;
import br.com.pj2.back.core.usecase.CheckScheduleConflictsUseCase;
import br.com.pj2.back.core.usecase.DenyMonitoringScheduleUseCase;
import br.com.pj2.back.core.usecase.FindSchedulesByFilterUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringScheduleRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Agendamento de Monitoria", description = "Gerenciamento de agendamentos de monitoria")
@RestController
@RequestMapping("/monitoring/schedules")
@RequiredArgsConstructor
public class MonitoringScheduleController {
    private final FindSchedulesByFilterUseCase findSchedulesByFilterUseCase;
    private final ApproveMonitoringScheduleUseCase approveMonitoringScheduleUseCase;
    private final DenyMonitoringScheduleUseCase denyMonitoringScheduleUseCase;
    private final TokenGateway tokenGateway;
    private final MonitoringScheduleGateway scheduleGateway;
    private final MonitoringGateway monitoringGateway;
    private final CheckScheduleConflictsUseCase checkScheduleConflictsUseCase;

    @Operation(summary = "Permite que um professor aprove um agendamento de monitoria")
    @PatchMapping("/teachers/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveSchedule(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        approveMonitoringScheduleUseCase.execute(authorizationHeader, id);
    }

    @Operation(summary = "Permite que um professor negue um agendamento de monitoria")
    @PatchMapping("/teachers/{id}/deny")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void denySchedule(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        denyMonitoringScheduleUseCase.execute(authorizationHeader, id);
    }

    @Operation(summary = "Buscar agendamentos de monitoria que o professor está relacionado por status")
    @GetMapping("/teachers/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<MonitoringScheduleResponse> findSchedulesByFilter(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Status do agendamento. Valores válidos: PENDING, APPROVED, DENIED.",
                    example = "APPROVED"
            )
            @RequestParam(required = false) String status,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        var schedules = findSchedulesByFilterUseCase.execute(status, authorizationHeader);
        return schedules.stream()
                .map(MonitoringScheduleResponse::of)
                .toList();
    }

    @Operation(summary = "Permite que um aluno solicite um agendamento de monitoria")
    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringScheduleResponse requestSchedule(
            @RequestBody @Valid MonitoringScheduleRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        checkScheduleConflictsUseCase.execute(request, registration);
        var newSchedule = scheduleGateway.save(
                MonitoringScheduleDomain.builder()
                        .monitorRegistration(registration)
                        .monitoring(request.getMonitoring())
                        .dayOfWeek(DayOfWeek.valueOf(request.getDayOfWeek().toUpperCase()))
                        .startTime(request.getStartTime())
                        .endTime(request.getEndTime())
                        .status(MonitoringScheduleStatus.PENDING)
                        .requestedAt(LocalDateTime.now())
                        .build()
        );
        return MonitoringScheduleResponse.of(newSchedule);
    }

    @Operation(summary = "Buscar agendamentos de monitoria do aluno")
    @GetMapping("/students/me")
    @ResponseStatus(HttpStatus.OK)
    public List<MonitoringScheduleResponse> mySchedules(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        var responses = new ArrayList<MonitoringScheduleResponse>();
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        var schedules = scheduleGateway.findByMonitorRegistration(registration);

        for (MonitoringScheduleDomain schedule : schedules) {
            var monitoring = monitoringGateway.findByName(schedule.getMonitoring());
            responses.add(MonitoringScheduleResponse.of(schedule, monitoring.getTopics()));
        }
        return responses;
    }

    @Operation(summary = "Buscar agendamento de monitoria por ID do aluno")
    @GetMapping("/students/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringScheduleResponse findScheduleById(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        var schedule = scheduleGateway.findByIdAndMonitorRegistration(id, registration);
        return MonitoringScheduleResponse.of(schedule);
    }
}
