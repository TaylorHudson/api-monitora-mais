package br.com.pj2.back.entrypoint.api.dto;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.StandardException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int statusCode;
    private String errorCode;
    private String errorMessage;
    private List<StandardException.FieldError> errorDetails;

    private ErrorResponse() {}

    private ErrorResponse(int statusCode, String errorCode, String errorMessage, List<StandardException.FieldError> errorDetails) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public static ErrorResponse of(int statusCode, ErrorCode errorCode, List<StandardException.FieldError> errorDetails) {
        return new ErrorResponse(statusCode, errorCode.getErrorCode(), errorCode.getMessage(), errorDetails);
    }

    public static ErrorResponse of(int statusCode, ErrorCode errorCode) {
        return ErrorResponse.of(statusCode, errorCode, null);
    }
}