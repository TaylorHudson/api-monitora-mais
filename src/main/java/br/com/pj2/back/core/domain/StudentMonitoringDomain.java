package br.com.pj2.back.core.domain;

import br.com.pj2.back.dataprovider.database.entity.StudentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentMonitoringDomain {

    private String name;
    private String registration;
    @Builder.Default
    List<String> daysOfWeek = new ArrayList<>();

    public static StudentMonitoringDomain of(StudentEntity studentEntity){
        return StudentMonitoringDomain.builder()
                .name(studentEntity.getName())
                .registration(studentEntity.getRegistration())
                .build();
    }
}
