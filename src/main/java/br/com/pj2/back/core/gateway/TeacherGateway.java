package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.Role;

public interface TeacherGateway {
    TeacherDomain findByRegistration(String registration);
    TeacherDomain findByRegistrationAndRole(String registration, Role role);
    TeacherDomain save(TeacherDomain domain);
    TeacherDomain findByName(String name);
}
