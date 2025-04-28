package br.com.pj2.back.entrypoint.api.config;

import br.com.pj2.back.core.domain.enumerated.BusinessError;
import br.com.pj2.back.entrypoint.api.dto.ErrorResponse;
import br.com.pj2.back.entrypoint.api.exception.PointNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(PointNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundException(PointNotFoundException exception) {
        return ErrorResponse.of(HttpStatus.NOT_FOUND.value(), BusinessError.POINT_NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        List<ErrorResponse.FieldError> errorDetails = fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError.of(error.getField(), error.getDefaultMessage()))
                .toList();
        return ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), BusinessError.VALIDATION_ERROR, errorDetails);
    }

}
