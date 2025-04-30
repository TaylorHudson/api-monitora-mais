package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.AuthDomain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(AuthDomain domain) {
        return new LoginResponse(domain.getAccessToken(), domain.getRefreshToken());
    }
}
