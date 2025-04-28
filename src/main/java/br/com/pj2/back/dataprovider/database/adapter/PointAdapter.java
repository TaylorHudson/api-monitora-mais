package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import br.com.pj2.back.dataprovider.database.entity.PointEntity;
import br.com.pj2.back.dataprovider.database.entity.mapper.MapperResquest;
import br.com.pj2.back.dataprovider.database.repository.PointRepository;
import br.com.pj2.back.entrypoint.api.exception.PointNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class PointAdapter implements PointGateway {

    private final PointRepository repository;

    public PointAdapter(PointRepository repository) {
        this.repository = repository;
    }

    @Override
    public PointDomain create(PointDomain domain) {
        var entity = repository.save(MapperResquest.toEntity(domain));
        return MapperResquest.toDomain(entity);
    }

    @Override
    public PointDomain update(Long id, PointDomain domain) {
        PointEntity entity = repository.findById(id).orElseThrow(PointNotFoundException::new);
        entity.setDescription(domain.getDescription());
        entity.setEndMonitoring(domain.getEndMonitoring());
        entity.setStartMonitoring(domain.getStartMonitoring());

        return MapperResquest.toDomain(repository.save(entity));
    }

    @Override
    public PointDomain getById(Long id) {
        var entity = repository.findById(id).orElseThrow(PointNotFoundException::new);
        return MapperResquest.toDomain(entity);
    }

    @Override
    public List<PointDomain> getAll() {
        return MapperResquest.toListDomain(repository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        getById(id);
        repository.deleteById(id);
    }

}