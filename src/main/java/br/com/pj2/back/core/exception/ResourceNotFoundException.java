package br.com.pj2.back.core.exception;

import br.com.pj2.back.core.domain.enumerated.ErrorCode;

import java.util.List;

public class ResourceNotFoundException extends StandardException {

    public ResourceNotFoundException(ErrorCode errorCode, List<FieldError> errorDetails) {
        super(errorCode, errorDetails);
    }

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
