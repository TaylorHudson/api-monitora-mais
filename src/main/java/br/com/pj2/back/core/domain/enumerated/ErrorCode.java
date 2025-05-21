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
    MONITORING_NOT_FOUND("ERROR_203", "Monitoria não encontrada."),
    MONITORING_SESSION_NOT_FOUND("ERROR_204", "Sessão de monitoria não encontrada."),
    // Authorization/Authentication errors - 300...399
    INVALID_TOKEN("ERROR_300", "Token de autenticação inválido, expirado ou corrompido."),
    INVALID_CREDENTIALS("ERROR_301", "Matrícula ou senha inválida."),
    // Conflict errors - 400...499
    MONITORING_SCHEDULE_REQUEST_CONFLICT("ERROR_400", "Já existe um agendamento de monitoria para essa disciplina nesse horário."),
    MONITORING_SESSION_ALREADY_STARTED("ERROR_401", "A sessão de monitoria já foi iniciada."),
    MONITORING_SESSION_CANNOT_BE_STARTED("ERROR_402", "A sessão de monitoria só pode ser iniciada no horário agendado ou em até 5 minutos depois."),
    MONITORING_SESSION_CANNOT_BE_FINISHED("ERROR_403", "A sessão de monitoria não foi iniciada, então não pode ser finalizada."),
    // Server errors - 900...999
    SERVER_ERROR("ERROR_999", "Não foi possível processar sua requisição.");

    private final String errorCode;
    private final String message;
}