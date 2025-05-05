package br.com.pj2.back.dataprovider.client.adapter;

import br.com.pj2.back.dataprovider.client.SuapClient;
import br.com.pj2.back.dataprovider.client.dto.AuthRequest;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthAdapterTest {

    @Mock
    private SuapClient suapClient;

    @InjectMocks
    private AuthAdapter authAdapter;

    private final String registration = "1234567";
    private final String password = "secret";

    @Test
    void shouldCallSuapClientWhenCredentialsAreValid() {
        AuthRequest request = new AuthRequest(registration, password);

        authAdapter.validateCredentials(registration, password);

        verify(suapClient).obtainToken(any(AuthRequest.class));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenFeignThrows() {
        AuthRequest request = new AuthRequest(registration, password);
        when(suapClient.obtainToken(request)).thenThrow(mock(FeignException.class));

        assertThrows(RuntimeException.class, () -> authAdapter.validateCredentials(registration, password));
    }
}