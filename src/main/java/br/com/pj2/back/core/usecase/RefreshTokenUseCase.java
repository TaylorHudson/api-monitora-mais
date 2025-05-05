package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.UnauthorizedException;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenUseCase {
    private final TokenGateway tokenGateway;

    public AuthDomain execute(String token) {
        AuthDomain auth;
        try {
            if (tokenGateway.isAccessToken(token)) {
                throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
            }

            String subject = tokenGateway.extractSubject(token);
            if (!tokenGateway.isTokenValid(token, subject)) {
                throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
            }

            String accessToken = tokenGateway.generateAccessToken(subject);
            auth = new AuthDomain(accessToken, null);
        } catch (Exception e) {
            log.error("Error during refreshing token - [{}].", e.getMessage(), e);
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }

        return auth;
    }
}
