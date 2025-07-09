package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.FindMissingWorkloadUseCase;
import br.com.pj2.back.entrypoint.api.dto.response.MissingWorkloadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Carga Horária Semanal")
@RestController
@RequestMapping("/weekly-workloads")
@RequiredArgsConstructor
public class WeeklyWorkloadController {
    private final FindMissingWorkloadUseCase findMissingWorkloadUseCase;

    @Operation(summary = "Buscar carga horária semanal faltante de um aluno")
    @GetMapping("/missing")
    @ResponseStatus(HttpStatus.OK)
    public MissingWorkloadResponse missingWeeklyWorkload(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        return new MissingWorkloadResponse(findMissingWorkloadUseCase.execute(authorizationHeader));
    }
}
