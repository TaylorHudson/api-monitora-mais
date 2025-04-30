package br.com.pj2.back.dataprovider.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @JsonProperty("username")
    private String registration;
    private String password;
}