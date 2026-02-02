package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class InternalServerException extends BusinessException {

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InternalServerException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
