package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class BadRequestException extends StandardException {

    public BadRequestException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
