package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.DisciplineDomain;

public interface DisciplineGateway {
    DisciplineDomain findByName(String name);
}
