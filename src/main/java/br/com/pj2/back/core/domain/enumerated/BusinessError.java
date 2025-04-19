package br.com.pj2.back.core.domain.enumerated;

//@Getter
public enum BusinessError {
    VALIDATION_ERROR("100", "Verifique os detalhes do erro para entender quais campos estão inválidos.");

    private final String code;
    private final String message;

    BusinessError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
