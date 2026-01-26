package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ERROR, message);
    }
}
