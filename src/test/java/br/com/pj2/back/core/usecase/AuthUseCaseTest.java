package br.com.pj2.back.core.usecase;

import static org.junit.jupiter.api.Assertions.*;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.domain.UserDomain;
import br.com.pj2.back.core.domain.enumerated.Role;
import br.com.pj2.back.core.gateway.AuthGateway;
import br.com.pj2.back.core.gateway.TokenGateway;
import br.com.pj2.back.core.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthUseCaseTest {
    @Mock
    private AuthGateway authGateway;
    @Mock
    private UserGateway userGateway;
    @Mock
    private TokenGateway tokenGateway;
    @InjectMocks
    private AuthUseCase authUseCase;

    @Test
    void shouldAuthenticateTeacherAndSaveUser() {
        String registration = "1234567";
        String password = "senha";
        String expectedAccess = "access-token";
        String expectedRefresh = "refresh-token";

        when(tokenGateway.generateAccessToken(registration)).thenReturn(expectedAccess);
        when(tokenGateway.generateRefreshToken(registration)).thenReturn(expectedRefresh);

        AuthDomain auth = authUseCase.execute(registration, password);

        verify(authGateway).validateCredentials(registration, password);
        verify(userGateway).save(any(UserDomain.class));
        assertEquals(expectedAccess, auth.getAccessToken());
        assertEquals(expectedRefresh, auth.getRefreshToken());
    }

    @Test
    void shouldAuthenticateExistingStudent() {
        String registration = "000000000000";
        String password = "senha";
        String expectedAccess = "access-token";
        String expectedRefresh = "refresh-token";

        when(userGateway.findByRegistrationAndRole(registration, Role.STUDENT)).thenReturn(new UserDomain(registration, Role.STUDENT));
        when(tokenGateway.generateAccessToken(registration)).thenReturn(expectedAccess);
        when(tokenGateway.generateRefreshToken(registration)).thenReturn(expectedRefresh);

        AuthDomain auth = authUseCase.execute(registration, password);

        verify(authGateway).validateCredentials(registration, password);
        verify(userGateway).findByRegistrationAndRole(registration, Role.STUDENT);
        assertEquals(expectedAccess, auth.getAccessToken());
        assertEquals(expectedRefresh, auth.getRefreshToken());
    }
}
