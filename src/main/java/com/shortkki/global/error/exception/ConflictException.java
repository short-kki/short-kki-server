package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class ConflictException extends BusinessException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
