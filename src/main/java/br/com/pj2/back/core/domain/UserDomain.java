package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDomain {
    private String id;
    private String registration;
    private List<Role> roles;
}
