package br.com.pj2.back.core.domain;

import br.com.pj2.back.core.domain.enumerated.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDomain {
    private String registration;
    private String name;
    private String email;
    private Role role;
    private int weeklyWorkload;
    private int missingWeeklyWorkload;

    public void subtractMissingWorkload(int durationInMinutes) {
        this.missingWeeklyWorkload -= durationInMinutes;
    }
}