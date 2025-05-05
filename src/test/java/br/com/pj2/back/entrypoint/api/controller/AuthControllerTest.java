package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.usecase.AuthUseCase;
import br.com.pj2.back.core.usecase.RefreshTokenUseCase;
import br.com.pj2.back.entrypoint.api.dto.LoginRequest;
import br.com.pj2.back.entrypoint.api.dto.RefreshTokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private AuthUseCase authUseCase;
    @Mock
    private RefreshTokenUseCase refreshTokenUseCase;
    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void shouldReturnAuthTokensOnLogin() throws Exception {
        LoginRequest request = new LoginRequest("202300123456", "password123");
        AuthDomain auth = new AuthDomain("access-token", "refresh-token");

        when(authUseCase.execute(request.getRegistration(), request.getPassword())).thenReturn(auth);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void shouldReturnNewAccessTokenOnRefresh() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest("refresh-token-123");
        AuthDomain auth = new AuthDomain("new-access-token", null);

        when(refreshTokenUseCase.execute(request.getRefreshToken())).thenReturn(auth);

        mockMvc.perform(post("/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access-token"))
                .andExpect(jsonPath("$.refreshToken").doesNotExist());
    }
}
