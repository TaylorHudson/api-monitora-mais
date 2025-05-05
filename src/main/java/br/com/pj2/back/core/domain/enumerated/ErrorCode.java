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
    // Authorization/Authentication errors - 300...399
    INVALID_TOKEN("ERROR_300", "Token de autenticação inválido, expirado ou corrompido."),
    // Server errors - 900...999
    SERVER_ERROR("ERROR_999", "Não foi possível processar sua requisição.");

    private final String errorCode;
    private final String message;
}