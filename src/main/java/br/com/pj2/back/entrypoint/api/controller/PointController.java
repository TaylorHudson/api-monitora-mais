package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.usecase.CreatePointUseCase;
import br.com.pj2.back.core.usecase.DeleteByIdUseCase;
import br.com.pj2.back.core.usecase.GetAllPointUseCase;
import br.com.pj2.back.core.usecase.GetByIdPointUseCase;
import br.com.pj2.back.core.usecase.UpdatePointUseCase;
import br.com.pj2.back.entrypoint.api.dto.PointRequest;
import br.com.pj2.back.entrypoint.api.dto.PointResponse;
import br.com.pj2.back.entrypoint.api.mapper.MapperResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/point")
//@RequiredArgsConstructor
public class PointController implements PointApi {

    private final CreatePointUseCase createPointUseCase;
    private final UpdatePointUseCase updatePointUseCase;
    private final GetByIdPointUseCase getByIdPointUseCase;
    private final GetAllPointUseCase getAllPointUseCase;
    private final DeleteByIdUseCase deleteByIdUseCase;

    public PointController(CreatePointUseCase createPointUseCase, UpdatePointUseCase updatePointUseCase, GetByIdPointUseCase getByIdPointUseCase, GetAllPointUseCase getAllPointUseCase, DeleteByIdUseCase deleteByIdUseCase) {
        this.createPointUseCase = createPointUseCase;
        this.updatePointUseCase = updatePointUseCase;
        this.getByIdPointUseCase = getByIdPointUseCase;
        this.getAllPointUseCase = getAllPointUseCase;
        this.deleteByIdUseCase = deleteByIdUseCase;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public PointResponse create(@Valid @RequestBody PointRequest request) {
        var domain = createPointUseCase.execute(MapperResponse.toDomain(request));
        return MapperResponse.toResponse(domain);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public PointResponse update(@PathVariable Long id, @Valid@RequestBody PointRequest request) {
        var domain = updatePointUseCase.execute(id, MapperResponse.toDomain(request));
        return MapperResponse.toResponse(domain);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public PointResponse getById(@PathVariable Long id) {
        var domain = getByIdPointUseCase.execute(id);
        return MapperResponse.toResponse(domain);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<PointResponse> getAll() {
        var domainList = getAllPointUseCase.execute();
        return MapperResponse.toListResponse(domainList);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        deleteByIdUseCase.execute(id);
    }

}
