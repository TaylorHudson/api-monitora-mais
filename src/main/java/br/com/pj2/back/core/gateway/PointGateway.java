package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.PointDomain;

public interface PointGateway {
    PointDomain create(PointDomain domain);
    PointDomain update(Long id, PointDomain domain);
}
