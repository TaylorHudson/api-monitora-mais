package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.entrypoint.api.dto.PointRequest;
import br.com.pj2.back.entrypoint.api.dto.PointResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ponto")
public interface PointApi {
    @Operation(summary = "Criar novo ponto")
    PointResponse create(PointRequest request);
    @Operation(summary = "Atualizar ponto existente")
    PointResponse update(Long id, PointRequest request);
}
