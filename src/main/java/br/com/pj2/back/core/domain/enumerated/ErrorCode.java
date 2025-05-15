package br.com.pj2.back.core.domain.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Validation errors - 100...199
    VALIDATION_ERROR("ERROR_100", "Verifique os detalhes do erro para entender quais campos estão inválidos."),
    // Resource not found errors - 200...299
    POINT_NOT_FOUND("ERROR_200", "Ponto não encontrado."),
    USER_NOT_FOUND("ERROR_201", "Usuário não encontrado."),
    MONITORING_SCHEDULE_NOT_FOUND("ERROR_202", "Agendamento de monitoria não encontrado."),
    DISCIPLINE_NOT_FOUND("ERROR_203", "Disciplina não encontrada."),
    // Authorization/Authentication errors - 300...399
    INVALID_TOKEN("ERROR_300", "Token de autenticação inválido, expirado ou corrompido."),
    INVALID_CREDENTIALS("ERROR_301", "Matrícula ou senha inválida."),
    // Conflict errors - 400...499
    MONITORING_SCHEDULE_REQUEST_CONFLICT("ERROR_400", "Já existe um agendamento de monitoria para essa disciplina nesse horário."),
    // Server errors - 900...999
    SERVER_ERROR("ERROR_999", "Não foi possível processar sua requisição.");

    private final String errorCode;
    private final String message;
}