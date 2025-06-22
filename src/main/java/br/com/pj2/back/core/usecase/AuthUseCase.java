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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final AuthGateway authGateway;
    private final TeacherGateway teacherGateway;
    private final StudentGateway studentGateway;
    private final TokenGateway tokenGateway;

    public AuthDomain execute(String registration, String password) {
        //authGateway.validateCredentials(registration, password);

        if (RegexUtils.isTeacherRegistration(registration)) {
            teacherGateway.save(TeacherDomain.builder().registration(registration).build());
            return generateAuthDomain(registration);
        }

        final var student =  studentGateway.findByRegistrationAndRole(registration, Role.STUDENT);
        return generateAuthDomain(student.getRegistration());
    }

    private AuthDomain generateAuthDomain(String registration) {
        String access = tokenGateway.generateAccessToken(registration);
        String refresh = tokenGateway.generateRefreshToken(registration);
        return new AuthDomain(access, refresh);
    }
}
