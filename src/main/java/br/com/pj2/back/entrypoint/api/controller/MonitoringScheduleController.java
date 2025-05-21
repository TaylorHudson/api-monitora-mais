package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.MonitoringScheduleDomain;
import br.com.pj2.back.core.domain.enumerated.MonitoringScheduleStatus;
import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.usecase.CheckScheduleConflictsUseCase;
import br.com.pj2.back.entrypoint.api.dto.MonitoringScheduleRequest;
import br.com.pj2.back.entrypoint.api.dto.MonitoringScheduleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/monitoring/schedules")
@RequiredArgsConstructor
public class MonitoringScheduleController {
    private final TokenGateway tokenGateway;
    private final MonitoringScheduleGateway scheduleGateway;
    private final CheckScheduleConflictsUseCase checkScheduleConflictsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringScheduleResponse requestSchedule(
            @RequestBody @Valid MonitoringScheduleRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        checkScheduleConflictsUseCase.execute(request);
        var newSchedule = scheduleGateway.save(
                MonitoringScheduleDomain.builder()
                        .monitor(registration)
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


    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public List<MonitoringScheduleResponse> mySchedulesByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        if (date == null) {
            date = LocalDate.now();
        }

        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        var schedules = scheduleGateway.findByMonitorRegistrationAndDayOfWeek(registration, date.getDayOfWeek());

        return schedules.stream()
                .map(MonitoringScheduleResponse::of)
                .toList();
    }

    @GetMapping("/{id}")
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
