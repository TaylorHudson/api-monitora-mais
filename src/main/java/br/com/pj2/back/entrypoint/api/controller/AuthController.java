package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.usecase.AuthUseCase;
import br.com.pj2.back.core.usecase.RefreshTokenUseCase;
import br.com.pj2.back.entrypoint.api.dto.request.LoginRequest;
import br.com.pj2.back.entrypoint.api.dto.response.LoginResponse;
import br.com.pj2.back.entrypoint.api.dto.request.RefreshTokenRequest;
import br.com.pj2.back.entrypoint.api.dto.response.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e tokens")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @Operation(summary = "Realizar login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@Valid @RequestBody LoginRequest request) {
        AuthDomain auth = authUseCase.execute(request.getRegistration(), request.getPassword());
        return LoginResponse.of(auth);
    }

    @Operation(summary = "Atualizar token de autenticação")
    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthDomain auth = refreshTokenUseCase.execute(request.getRefreshToken());
        return RefreshTokenResponse.of(auth);
    }
}
