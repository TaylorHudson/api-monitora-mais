package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class ConflictException extends StandardException {

    public ConflictException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
