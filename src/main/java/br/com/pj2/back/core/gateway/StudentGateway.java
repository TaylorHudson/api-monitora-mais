package br.com.pj2.back.core.gateway;

import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.Role;

public interface StudentGateway {
    StudentDomain findByRegistration(String registration);
    StudentDomain findByRegistrationAndRole(String registration, Role role);
    StudentDomain save(StudentDomain domain);
}
