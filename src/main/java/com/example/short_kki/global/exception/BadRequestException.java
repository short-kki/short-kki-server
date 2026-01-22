package com.example.short_kki.global.exception;

public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
