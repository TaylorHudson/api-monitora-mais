package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMissingWorkloadUseCase {
    private final StudentGateway studentGateway;
    private final TokenGateway tokenGateway;

    public int execute(String authorizationHeader) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        return studentGateway.findByRegistration(registration).getMissingWeeklyWorkload();
    }
}
