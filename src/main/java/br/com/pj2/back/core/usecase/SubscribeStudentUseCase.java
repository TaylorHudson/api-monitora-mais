package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.MonitoringDomain;
import br.com.pj2.back.core.domain.StudentDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.BadRequestException;
import br.com.pj2.back.core.gateway.AuthGateway;
import br.com.pj2.back.core.gateway.MonitoringGateway;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.entrypoint.api.dto.request.SubscribeStudentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeStudentUseCase {

    private final MonitoringGateway monitoringGateway;
    private final StudentGateway studentGateway;
    private final TokenGateway tokenGateway;
    private final AuthGateway authGateway;

    public void execute(SubscribeStudentRequest request, String authorizationHeader) {
        authGateway.checkIfStudentExists(request.getStudentRegistration());

        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);

        MonitoringDomain monitoring = monitoringGateway.findByName(request.getMonitoringName());
        if (!monitoring.getTeacher().equals(registration)) {
            throw new BadRequestException(ErrorCode.TEACHER_IS_NOT_THE_TUTORING_TEACHER);
        }

        if (monitoring.containsStudent(request.getStudentRegistration())) {
            throw new BadRequestException(ErrorCode.STUDENT_ALREADY_SUBSCRIBED);
        }

        StudentDomain student = StudentDomain.builder()
                .registration(request.getStudentRegistration())
                .build();
        studentGateway.save(student);

        monitoring.subscribeStudent(request.getStudentRegistration());
        monitoringGateway.create(monitoring);
    }
}
