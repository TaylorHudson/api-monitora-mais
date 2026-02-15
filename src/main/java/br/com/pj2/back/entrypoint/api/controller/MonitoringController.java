package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringDomainDetail;
import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.CreateMonitoringUseCase;
import br.com.pj2.back.core.usecase.DeleteMonitoringByIdUseCase;
import br.com.pj2.back.core.usecase.FindAllMonitoringUseCase;
import br.com.pj2.back.core.usecase.FindMonitoringByIdUseCase;
import br.com.pj2.back.core.usecase.FindMonitoringDetailsByIdUseCase;
import br.com.pj2.back.core.usecase.SubscribeStudentUseCase;
import br.com.pj2.back.core.usecase.UpdateMonitoringUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringRequest;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringUpdateRequest;
import br.com.pj2.back.entrypoint.api.dto.request.SubscribeStudentRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringDetailsResponse;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringResponse;
import br.com.pj2.back.entrypoint.api.dto.response.MyMonitoringResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Monitoria", description = "Gerenciamento de monitorias")
@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final CreateMonitoringUseCase createMonitoringUseCase;
    private final SubscribeStudentUseCase subscribeStudentUseCase;
    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;
    private final FindAllMonitoringUseCase findAllMonitoringUseCase;
    private final DeleteMonitoringByIdUseCase deleteMonitoringByIdUseCase;
    private final UpdateMonitoringUseCase updateMonitoringUseCase;
    private final FindMonitoringByIdUseCase findMonitoringByIdUseCase;
    private final FindMonitoringDetailsByIdUseCase findMonitoringDetailsByIdUseCase;

    @Operation(summary = "Permite que um professor crie uma nova monitoria")
    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringResponse create(
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        return MonitoringResponse.of(createMonitoringUseCase.execute(request, authorizationHeader));
    }

    @Operation(summary = "Permite que um professor delete uma monitoria")
    @DeleteMapping("/teachers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        deleteMonitoringByIdUseCase.execute(id, authorizationHeader);
    }

    @Operation(summary = "Buscar todas as monitorias do professor")
    @GetMapping("/teachers/me")
    @ResponseStatus(HttpStatus.OK)
    public List<MonitoringResponse> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return findAllMonitoringUseCase.execute(authorizationHeader).stream().map(MonitoringResponse::of).toList();
    }

    @Operation(summary = "Atualizar uma monitoria")
    @PutMapping("/teachers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringResponse update(
            @PathVariable Long id,
            @RequestBody @Valid MonitoringUpdateRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws BindException {
        return MonitoringResponse.of(updateMonitoringUseCase.execute(id, request, authorizationHeader));
    }

    @Operation(summary = "Buscar uma monitoria")
    @GetMapping("/teachers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringResponse findById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return MonitoringResponse.of(findMonitoringByIdUseCase.execute(id, authorization));
    }

    @Operation(summary = "Buscar detalhes de uma monitoria")
    @GetMapping("/teachers/details/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringDetailsResponse findByIdMonitoringDetails(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return MonitoringDetailsResponse.of(findMonitoringDetailsByIdUseCase.execute(id, authorization));
    }

    @Operation(summary = "Inscrever um aluno em uma monitoria")
    @PostMapping("/students/subscribe")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeStudent(
            @RequestBody @Valid SubscribeStudentRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        subscribeStudentUseCase.execute(request, authorizationHeader);
    }

    @Operation(summary = "Buscar todas as monitorias do aluno")
    @GetMapping("/students/me")
    @ResponseStatus(HttpStatus.OK)
    public List<MyMonitoringResponse> myMonitoring(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        return monitoringGateway.findAllByStudentRegistration(registration).stream()
                .map(monitoring -> MyMonitoringResponse.builder()
                        .id(monitoring.getId())
                        .name(monitoring.getName())
                        .teacher(monitoring.getTeacher())
                        .alreadyRequested(hasStudentSchedule(monitoring.getSchedules(), registration))
                        .build())
                .toList();
    }

    private boolean hasStudentSchedule(List<MonitoringScheduleDomain> schedules, String registration) {
        if (schedules == null) return false;
        return schedules.stream()
                .anyMatch(schedule -> registration.equals(schedule.getMonitor()));
    }
}
