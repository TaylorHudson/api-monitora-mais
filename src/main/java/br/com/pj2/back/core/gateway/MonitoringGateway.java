package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.MonitoringDomain;

public interface MonitoringGateway {
    MonitoringDomain findByName(String name);
}
