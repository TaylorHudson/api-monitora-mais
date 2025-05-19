package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.MonitoringSessionDomain;

public interface MonitoringSessionGateway {
    MonitoringSessionDomain save(MonitoringSessionDomain domain);
    MonitoringSessionDomain findByMonitorAndIsStartedTrue(String monitorRegistration);
}
