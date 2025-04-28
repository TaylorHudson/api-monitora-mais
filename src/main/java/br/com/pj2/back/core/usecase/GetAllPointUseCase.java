package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetAllPointUseCase {

    private final PointGateway pointGateway;

    public GetAllPointUseCase(PointGateway pointGateway) {
        this.pointGateway = pointGateway;
    }

    public List<PointDomain> execute() {
        return pointGateway.getAll();
    }
}
