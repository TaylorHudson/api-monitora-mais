package br.com.pj2.back.entrypoint.api.config;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;
import br.com.pj2.back.core.exception.*;
import br.com.pj2.back.entrypoint.api.dto.ErrorResponse;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BindException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        List<StandardException.FieldError> errorDetails = fieldErrors.stream()
                .map(error -> StandardException.FieldError.of(error.getField(), error.getDefaultMessage()))
                .toList();

        log.error("Bind Error - [{}]", ErrorCode.VALIDATION_ERROR.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), ErrorCode.VALIDATION_ERROR, errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(BadRequestException exception) {
        log.error("BadRequest Error - [{}]", exception.getErrorCode().getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(ResourceNotFoundException exception) {
        log.error("ResourceNotFound Error - [{}]", exception.getErrorCode().getMessage());
        return ErrorResponse.of(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(UnauthorizedException exception) {
        log.error("Unauthorized Error - [{}]", exception.getErrorCode().getMessage());
        return ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), exception.getErrorCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handle(ForbiddenException exception) {
        log.error("Forbidden Error - [{}]", exception.getErrorCode().getMessage());
        return ErrorResponse.of(HttpStatus.FORBIDDEN.value(), exception.getErrorCode());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handle(JwtException exception) {
        log.error("JWT Error - [{}]", exception.getMessage(), exception);
        return ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), ErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(ConflictException exception) {
        log.error("Conflict Error - [{}]", exception.getMessage(), exception);
        return ErrorResponse.of(HttpStatus.CONFLICT.value(), exception.getErrorCode());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Throwable exception) {
        log.error("Generic Error - [{}]", exception.getMessage(), exception);
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.SERVER_ERROR);
    }
}