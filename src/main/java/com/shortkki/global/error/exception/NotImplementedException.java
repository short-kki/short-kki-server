package com.shortkki.global.error.exception;

import com.shortkki.global.error.ErrorCode;

public class NotImplementedException extends BusinessException {

    public NotImplementedException() {
        super(ErrorCode.NOT_IMPLEMENTED_ERROR);
    }
}
