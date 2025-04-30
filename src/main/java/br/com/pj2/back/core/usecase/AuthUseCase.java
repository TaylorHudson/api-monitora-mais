package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.gateway.AuthGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {
    private final AuthGateway authGateway;
    private final UserGateway userGateway;
    private final TokenGateway tokenGateway;

    public AuthDomain execute(String registration, String password) {
        authGateway.validateCredentials(registration, password);
        final var user =  userGateway.findByRegistration(registration).orElseThrow();
        String access = tokenGateway.generateAccessToken(user.getRegistration());
        String refresh = tokenGateway.generateRefreshToken(user.getRegistration());
        return new AuthDomain(access, refresh);
    }
}
