package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class ForbiddenException extends StandardException {

    public ForbiddenException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
