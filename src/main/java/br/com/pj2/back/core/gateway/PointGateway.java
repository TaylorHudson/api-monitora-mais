package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.PointDomain;

import java.util.List;

public interface PointGateway {
    PointDomain create(PointDomain domain);
    PointDomain update(Long id, PointDomain domain);

    PointDomain getById(Long id);

    List<PointDomain> getAll();

    void deleteById(Long id);
}
