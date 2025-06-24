package br.com.pj2.back.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotEmpty(message = "A matrícula é obrigatória")
    private String registration;
    @NotEmpty(message = "A senha é obrigatória")
    private String password;
}
