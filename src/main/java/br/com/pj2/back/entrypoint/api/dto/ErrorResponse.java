package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.enumerated.BusinessError;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

//@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int statusCode;
    private LocalDateTime timestamp;
    private String errorCode;
    private String errorMessage;
    private List<FieldError> errorDetails;

    private ErrorResponse() {}

    private ErrorResponse(int statusCode, String errorCode, String errorMessage, List<FieldError> errorDetails) {
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public static ErrorResponse of(int statusCode, BusinessError businessError, List<FieldError> errorDetails) {
        return new ErrorResponse(statusCode, businessError.getCode(), businessError.getMessage(), errorDetails);
    }

    public static ErrorResponse of(int statusCode, BusinessError businessError) {
        return ErrorResponse.of(statusCode, businessError, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<FieldError> getErrorDetails() {
        return errorDetails;
    }

    //@Getter
    public static class FieldError {
        private String field;
        private String message;

        private FieldError() {}

        private FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        public static FieldError of(String field, String message) {
            return new FieldError(field, message);
        }
    }


}
