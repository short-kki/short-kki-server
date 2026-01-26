package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(ErrorCode.ACCESS_DENIED, message);
    }
}
