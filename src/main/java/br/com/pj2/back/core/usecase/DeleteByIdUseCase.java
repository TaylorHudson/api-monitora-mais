package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.PointGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteByIdUseCase {

    private final PointGateway pointGateway;

    public DeleteByIdUseCase(PointGateway pointGateway) {
        this.pointGateway = pointGateway;
    }

    public void execute(Long id) {
        pointGateway.deleteById(id);
    }
}
