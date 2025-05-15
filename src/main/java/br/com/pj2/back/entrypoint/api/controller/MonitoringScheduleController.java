package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.gateway.MonitoringScheduleGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.entrypoint.api.dto.MonitoringScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/monitoring/schedules")
@RequiredArgsConstructor
public class MonitoringScheduleController {
    private final TokenGateway tokenGateway;
    private final MonitoringScheduleGateway scheduleGateway;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<MonitoringScheduleResponse> requestSchedule(
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
