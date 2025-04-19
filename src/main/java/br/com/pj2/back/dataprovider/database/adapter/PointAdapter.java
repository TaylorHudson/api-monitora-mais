package br.com.pj2.back.dataprovider.database.adapter;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import br.com.pj2.back.dataprovider.database.entity.mapper.PointMapper;
import br.com.pj2.back.dataprovider.database.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class PointAdapter implements PointGateway {

    private final PointRepository repository;

    public PointAdapter(PointRepository repository) {
        this.repository = repository;
    }

    @Override
    public PointDomain create(PointDomain domain) {
        var entity = repository.save(PointMapper.INSTANCE.toEntity(domain));
        return PointMapper.INSTANCE.toDomain(entity);
    }

    @Override
    public PointDomain update(Long id, PointDomain domain) {
        domain.setId(id);
        var entity = repository.save(PointMapper.INSTANCE.toEntity(domain));
        return PointMapper.INSTANCE.toDomain(entity);
    }

}