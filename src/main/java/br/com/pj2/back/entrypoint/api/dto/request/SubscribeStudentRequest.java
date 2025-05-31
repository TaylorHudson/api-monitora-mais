package br.com.pj2.back.entrypoint.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeStudentRequest {
    @NotEmpty(message = "A matrícula do aluno é obrigatória.")
    private String studentRegistration;
    @NotEmpty(message = "O nome da monitoria é obrigatório.")
    private String monitoringName;
}
