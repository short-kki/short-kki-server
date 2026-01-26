package com.example.short_kki.global.error.exception;

import com.example.short_kki.global.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}
