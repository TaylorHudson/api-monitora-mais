package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDomain {
    private String registration;
    private Role role;
}