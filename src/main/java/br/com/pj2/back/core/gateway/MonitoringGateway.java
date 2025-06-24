package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.MonitoringDomain;

import java.util.List;

public interface MonitoringGateway {
    MonitoringDomain findByName(String name);
    MonitoringDomain create(MonitoringDomain domain);
    List<MonitoringDomain> findAll(String registration);
    void deleteById(Long id, String registration);
    MonitoringDomain update(Long id, MonitoringDomain domain, String registration);
    MonitoringDomain findById(Long id);
}
