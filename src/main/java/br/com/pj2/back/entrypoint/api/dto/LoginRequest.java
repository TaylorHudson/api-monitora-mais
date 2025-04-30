package br.com.pj2.back.entrypoint.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    private String registration;
    private String password;
}
