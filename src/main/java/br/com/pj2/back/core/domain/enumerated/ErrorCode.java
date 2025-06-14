package br.com.pj2.back.core.domain.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Validation errors - 100...199
    VALIDATION_ERROR("ERROR_100", "Verifique os detalhes do erro para entender quais campos estão inválidos."),
    TEACHER_IS_NOT_THE_TUTORING_TEACHER("ERROR_101", "O professor informado não é o professor da monitoria."),
    STUDENT_ALREADY_SUBSCRIBED("ERROR_102", "O estudante já está inscrito na monitoria."),
    INVALID_STUDENT_REGISTRATION("ERROR_103", "A matrícula do estudante é inválida ou não foi encontrada no Suap."),
    // Resource not found errors - 200...299
    POINT_NOT_FOUND("ERROR_200", "Ponto não encontrado."),
    USER_NOT_FOUND("ERROR_201", "Usuário não encontrado."),
    MONITORING_SCHEDULE_NOT_FOUND("ERROR_202", "Agendamento de monitoria não encontrado."),
    MONITORING_NOT_FOUND("ERROR_203", "Monitoria não encontrada."),
    MONITORING_SESSION_NOT_FOUND("ERROR_204", "Sessão de monitoria não encontrada."),
    NO_STARTED_MONITORING_SESSION_WAS_FOUND("ERROR_205", "Nenhuma sessão de monitoria iniciada foi encontrada."),
    // Authorization/Authentication errors - 300...399
    INVALID_TOKEN("ERROR_300", "Token de autenticação inválido, expirado ou corrompido."),
    INVALID_CREDENTIALS("ERROR_301", "Matrícula ou senha inválida."),
    // Conflict errors - 400...499
    MONITORING_SCHEDULE_REQUEST_CONFLICT("ERROR_400", "Já existe um agendamento de monitoria para essa disciplina nesse horário."),
    MONITORING_SESSION_ALREADY_STARTED("ERROR_401", "A sessão de monitoria já foi iniciada."),
    MONITORING_SESSION_CANNOT_BE_STARTED("ERROR_402", "A sessão de monitoria só pode ser iniciada no horário agendado ou em até 5 minutos depois."),
    MONITORING_SESSION_CANNOT_BE_FINISHED("ERROR_403", "A sessão de monitoria não foi iniciada, então não pode ser finalizada."),
    // Forbidden errors - 500...599
    DO_NOT_HAVE_PERMISSION_TO_APPROVE("ERROR_500", "Você não tem permissão para aprovar o agendamento de monitoria."),
    // Server errors - 900...999
    SERVER_ERROR("ERROR_999", "Não foi possível processar sua requisição.");

    private final String errorCode;
    private final String message;
}