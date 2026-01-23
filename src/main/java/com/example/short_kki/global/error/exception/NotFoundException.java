package com.example.short_kki.global.error.exception;

import com.example.short_kki.global.error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ERROR, message);
    }
}
