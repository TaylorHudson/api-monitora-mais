package br.com.pj2.back.core.usecase;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.exception.UnauthorizedException;
import br.com.pj2.back.core.gateway.TokenGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenUseCaseTest {
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private RefreshTokenUseCase refreshTokenUseCase;

    @Test
    void shouldReturnNewAccessTokenWhenRefreshTokenIsValid() {
        String refreshToken = "valid-refresh-token";
        String subject = "user123";
        String newAccessToken = "new-access-token";

        when(tokenGateway.isAccessToken(refreshToken)).thenReturn(false);
        when(tokenGateway.extractSubject(refreshToken)).thenReturn(subject);
        when(tokenGateway.isTokenValid(refreshToken, subject)).thenReturn(true);
        when(tokenGateway.generateAccessToken(subject)).thenReturn(newAccessToken);

        AuthDomain result = refreshTokenUseCase.execute(refreshToken);

        assertEquals(newAccessToken, result.getAccessToken());
        assertNull(result.getRefreshToken());
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenTokenIsAccessToken() {
        String accessToken = "access-token";
        when(tokenGateway.isAccessToken(accessToken)).thenReturn(true);

        assertThrows(UnauthorizedException.class, () -> refreshTokenUseCase.execute(accessToken));
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenRefreshTokenIsInvalid() {
        String refreshToken = "invalid-refresh-token";
        String subject = "user123";

        when(tokenGateway.isAccessToken(refreshToken)).thenReturn(false);
        when(tokenGateway.extractSubject(refreshToken)).thenReturn(subject);
        when(tokenGateway.isTokenValid(refreshToken, subject)).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> refreshTokenUseCase.execute(refreshToken));
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenExceptionOccursDuringTokenValidation() {
        String refreshToken = "exception-token";
        when(tokenGateway.isAccessToken(refreshToken)).thenReturn(false);
        when(tokenGateway.extractSubject(refreshToken)).thenThrow(new RuntimeException("Unexpected error"));

        assertThrows(UnauthorizedException.class, () -> refreshTokenUseCase.execute(refreshToken));
    }
}
