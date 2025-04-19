package br.com.pj2.back.entrypoint.api.config;

import br.com.pj2.back.core.domain.enumerated.BusinessError;
import br.com.pj2.back.entrypoint.api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BindException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        List<ErrorResponse.FieldError> errorDetails = fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError.of(error.getField(), error.getDefaultMessage()))
                .toList();
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), BusinessError.VALIDATION_ERROR, errorDetails);
    }

}
