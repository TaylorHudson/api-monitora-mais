package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.CreatePointUseCase;
import br.com.pj2.back.core.usecase.UpdatePointUseCase;
import br.com.pj2.back.entrypoint.api.dto.PointRequest;
import br.com.pj2.back.entrypoint.api.dto.PointResponse;
import br.com.pj2.back.entrypoint.api.mapper.PointDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/point")
//@RequiredArgsConstructor
public class PointController implements PointApi {

    private final CreatePointUseCase createPointUseCase;
    private final UpdatePointUseCase updatePointUseCase;

    public PointController(CreatePointUseCase createPointUseCase, UpdatePointUseCase updatePointUseCase) {
        this.createPointUseCase = createPointUseCase;
        this.updatePointUseCase = updatePointUseCase;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public PointResponse create(@Valid @RequestBody PointRequest request) {
        var domain = createPointUseCase.execute(PointDtoMapper.INSTANCE.toDomain(request));
        return PointDtoMapper.INSTANCE.toResponse(domain);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public PointResponse update(@PathVariable Long id, @Valid@RequestBody PointRequest request) {
        var domain = updatePointUseCase.execute(id, PointDtoMapper.INSTANCE.toDomain(request));
        return PointDtoMapper.INSTANCE.toResponse(domain);
    }
}
