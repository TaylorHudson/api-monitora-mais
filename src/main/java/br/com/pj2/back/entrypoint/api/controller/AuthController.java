package br.com.pj2.back.entrypoint.api.controller;

import br.com.pj2.back.core.domain.AuthDomain;
import br.com.pj2.back.core.usecase.AuthUseCase;
import br.com.pj2.back.entrypoint.api.dto.LoginRequest;
import br.com.pj2.back.entrypoint.api.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthUseCase authUseCase;

    @PostMapping("/auth/login")
    public LoginResponse authenticate(@RequestBody LoginRequest request) {
        AuthDomain auth = authUseCase.execute(request.getRegistration(), request.getPassword());
        return LoginResponse.of(auth);
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint";
    }
}
