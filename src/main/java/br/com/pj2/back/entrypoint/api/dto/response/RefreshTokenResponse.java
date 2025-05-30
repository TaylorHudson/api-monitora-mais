package br.com.pj2.back.entrypoint.api.dto.response;

import br.com.pj2.back.core.domain.AuthDomain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenResponse {
    private String accessToken;

    public static RefreshTokenResponse of(AuthDomain domain) {
        return new RefreshTokenResponse(domain.getAccessToken());
    }
}
