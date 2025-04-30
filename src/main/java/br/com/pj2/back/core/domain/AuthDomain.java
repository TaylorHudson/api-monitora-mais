package br.com.pj2.back.core.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDomain {
    private String accessToken;
    private String refreshToken;
}
