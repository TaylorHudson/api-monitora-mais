package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
//@Slf4j
public class UpdatePointUseCase {

    private final PointGateway pointGateway;

    public UpdatePointUseCase(PointGateway pointGateway) {
        this.pointGateway = pointGateway;
    }

    public PointDomain execute(Long id, PointDomain domain) {
        //log.info("Updating a existing point. [id={}]", id);
        return pointGateway.update(id, domain);
    }
}
