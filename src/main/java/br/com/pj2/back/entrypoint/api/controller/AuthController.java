package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.usecase.AuthUseCase;
import br.com.pj2.back.core.usecase.RefreshTokenUseCase;
import br.com.pj2.back.entrypoint.api.dto.LoginRequest;
import br.com.pj2.back.entrypoint.api.dto.LoginResponse;
import br.com.pj2.back.entrypoint.api.dto.RefreshTokenRequest;
import br.com.pj2.back.entrypoint.api.dto.RefreshTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public LoginResponse authenticate(@Valid @RequestBody LoginRequest request) {
        AuthDomain auth = authUseCase.execute(request.getRegistration(), request.getPassword());
        return LoginResponse.of(auth);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthDomain auth = refreshTokenUseCase.execute(request.getRefreshToken());
        return RefreshTokenResponse.of(auth);
    }
}
