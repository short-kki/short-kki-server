package com.example.short_kki.global.error.exception;

import com.example.short_kki.global.error.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String message) {
        super(ErrorCode.ACCESS_DENIED, message);
    }
}
