package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.PointDomain;
import br.com.pj2.back.core.gateway.PointGateway;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
//@Slf4j
public class CreatePointUseCase {

    private final PointGateway pointGateway;

    public CreatePointUseCase(PointGateway pointGateway) {
        this.pointGateway = pointGateway;
    }

    public PointDomain execute(PointDomain domain) {
        //log.info("Creating a new point.");
        return pointGateway.create(domain);
    }
}
