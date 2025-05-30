package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.MonitoringDomain;

import java.util.List;

public interface MonitoringGateway {
    MonitoringDomain findByName(String name);
    MonitoringDomain create(MonitoringDomain domain);
    List<MonitoringDomain> findAllByStudentRegistration(String registration);
}
