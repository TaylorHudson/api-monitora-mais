package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import org.springframework.stereotype.Service;

@Service
public class GetByIdPointUseCase {

    private final PointGateway pointGateway;

    public GetByIdPointUseCase(PointGateway pointGateway) {
        this.pointGateway = pointGateway;
    }

    public PointDomain execute(Long id) {
        return pointGateway.getById(id);
    }
}
