package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDomain {
    private String registration;
    private String name;
    private String email;
    private Role role;
    // private Set<DisciplineDomain> disciplines = new HashSet<>();
}