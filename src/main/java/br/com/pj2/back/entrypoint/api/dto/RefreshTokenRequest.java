package br.com.pj2.back.entrypoint.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenRequest {
    @NotEmpty(message = "O token é obrigatório")
    private String refreshToken;
}
