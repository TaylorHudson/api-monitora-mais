package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.common.utils.TimeUtils;
import br.com.pj2.back.core.gateway.StudentGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class FindMissingWorkloadUseCase {
    private final StudentGateway studentGateway;
    private final TokenGateway tokenGateway;

    public LocalTime execute(String authorizationHeader) {
        String registration = tokenGateway.extractSubjectFromAuthorization(authorizationHeader);
        var missingWorkload = studentGateway.findByRegistration(registration).getMissingWeeklyWorkload();
        return TimeUtils.minutesToLocalTime(missingWorkload);
    }
}
