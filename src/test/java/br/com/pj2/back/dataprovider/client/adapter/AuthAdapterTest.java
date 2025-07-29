package br.com.pj2.back.dataprovider.client.adapter;

import br.com.pj2.back.dataprovider.client.SuapClient;
import br.com.pj2.back.dataprovider.client.dto.AuthRequest;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthAdapterTest {

    private final String registration = "1234567";
    private final String password = "secret";
    private final String key = "key";
    @Mock
    private SuapClient suapClient;
    private AuthAdapter authAdapter;

    @BeforeEach
    void setUp() {
        authAdapter = new AuthAdapter(suapClient, "ja+iVt4M9whY81eSaPt/DQbyynK9eyIy3ejr4AR5VtfoW0=", "zYxWvUtsRqPoNmkL");
    }

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
