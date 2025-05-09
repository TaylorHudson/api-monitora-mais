package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class UnauthorizedException extends StandardException {

    public UnauthorizedException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
