package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.CreateMonitoringUseCase;
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

    private final CreateMonitoringUseCase createMonitoringUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MonitoringResponse create(
            @RequestBody @Valid MonitoringRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws BindException {
        return MonitoringResponse.of(createMonitoringUseCase.execute(request, authorizationHeader));
    }
}
