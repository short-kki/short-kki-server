package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}
