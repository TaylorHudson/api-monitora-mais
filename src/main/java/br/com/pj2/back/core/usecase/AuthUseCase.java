package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.common.utils.RegexUtils;
import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.domain.TeacherDomain;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.gateway.AuthGateway;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TeacherGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUseCase {
    private final AuthGateway authGateway;
    private final TeacherGateway teacherGateway;
    private final StudentGateway studentGateway;
    private final TokenGateway tokenGateway;

    public AuthDomain execute(String registration, String password) {
        //authGateway.validateCredentials(registration, password);
        log.info("[AuthUseCase] User credentials validated in SUAP");

        if (RegexUtils.isTeacherRegistration(registration)) {
            log.info("[AuthUseCase] User is a teacher");
            teacherGateway.save(TeacherDomain.builder().registration(registration).build());
            return generateAuthDomain(registration);
        }

        log.info("[AuthUseCase] User is a student, verifying if has permission to login");
        final var student = studentGateway.findByRegistrationAndRole(registration, Role.STUDENT);
        return generateAuthDomain(student.getRegistration());
    }

    private AuthDomain generateAuthDomain(String registration) {
        String access = tokenGateway.generateAccessToken(registration);
        String refresh = tokenGateway.generateRefreshToken(registration);
        return new AuthDomain(access, refresh);
    }
}
