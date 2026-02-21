package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class UnprocessableEntityException extends StandardException {

    public UnprocessableEntityException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public UnprocessableEntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
