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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final CreateMonitoringUseCase createMonitoringUseCase;
    private final SubscribeStudentUseCase subscribeStudentUseCase;
    private final MonitoringGateway monitoringGateway;
    private final TokenGateway tokenGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringResponse create(
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        return MonitoringResponse.of(createMonitoringUseCase.execute(request, authorizationHeader));
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeStudent(
            @RequestBody @Valid SubscribeStudentRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        subscribeStudentUseCase.execute(request, authorizationHeader);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public List<MyMonitoringResponse> myMonitoring(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
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
