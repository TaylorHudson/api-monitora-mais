package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class StandardException extends RuntimeException {
    private final ErrorCode errorCode;
    private List<FieldError> errorDetails;

    protected StandardException(ErrorCode errorCode, List<FieldError> errorDetails) {
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
    }

    protected StandardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @AllArgsConstructor
    @Getter
    public static class FieldError {
        private String field;
        private String message;

        public static FieldError of(String field, String message) {
            return new FieldError(field, message);
        }
    }
}