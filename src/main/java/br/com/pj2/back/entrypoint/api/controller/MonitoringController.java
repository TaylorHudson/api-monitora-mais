package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.CreateMonitoringUseCase;
import br.com.pj2.back.core.usecase.SubscribeStudentUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.MonitoringRequest;
import br.com.pj2.back.entrypoint.api.dto.request.SubscribeStudentRequest;
import br.com.pj2.back.entrypoint.api.dto.response.MonitoringResponse;
import br.com.pj2.back.entrypoint.api.dto.response.MyMonitoringResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.com.pj2.back.core.usecase.DeleteByIdMonitoringUseCase;
import br.com.pj2.back.core.usecase.FindAllMonitoringUseCase;
import br.com.pj2.back.core.usecase.FindByIdMonitoring;
import br.com.pj2.back.core.usecase.UpdateMonitoringUseCase;
import br.com.pj2.back.entrypoint.api.dto.MonitoringRequest;
import br.com.pj2.back.entrypoint.api.dto.MonitoringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
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
import java.util.List;

@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final CreateMonitoringUseCase createMonitoringUseCase;
    private final SubscribeStudentUseCase subscribeStudentUseCase;
    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;
    private final FindAllMonitoringUseCase findAllMonitoringUseCase;
    private final DeleteByIdMonitoringUseCase deleteByIdMonitoringUseCase;
    private final UpdateMonitoringUseCase updateMonitoringUseCase;
    private final FindByIdMonitoring findByIdMonitoring;

    @Operation(summary = "Criar uma nova monitoria")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringResponse create(
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        return MonitoringResponse.of(createMonitoringUseCase.execute(request, authorizationHeader));
    }

    @Operation(summary = "Inscrever um aluno em uma monitoria")
    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeStudent(
            @RequestBody @Valid SubscribeStudentRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        subscribeStudentUseCase.execute(request, authorizationHeader);
    }

    @Operation(summary = "Buscar minhas monitorias")
    @GetMapping("/me")
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MonitoringResponse> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws BindException{
        return findAllMonitoringUseCase.execute(authorizationHeader).stream().map(MonitoringResponse::of).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws BindException {
        deleteByIdMonitoringUseCase.execute(id, authorizationHeader);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringResponse update(
            @PathVariable Long id,
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws BindException {
        return MonitoringResponse.of(updateMonitoringUseCase.execute(id, request, authorizationHeader));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MonitoringResponse findById(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws BindException{
        return MonitoringResponse.of(findByIdMonitoring.execute(id, authorization));
    }
}
