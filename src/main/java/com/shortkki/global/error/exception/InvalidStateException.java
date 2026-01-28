package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class InvalidStateException extends BusinessException {

    public InvalidStateException(String message) {
        super(ErrorCode.RESOURCE_CONFLICT, message);
    }
}
