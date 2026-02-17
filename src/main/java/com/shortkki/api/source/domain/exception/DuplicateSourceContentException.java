package com.shortkki.api.source.domain.exception;

import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import java.util.Map;
import lombok.Getter;

@Getter
public class DuplicateSourceContentException extends BusinessException {

    private final Map<String, Object> data;

    public DuplicateSourceContentException(ErrorCode errorCode, Map<String, Object> data) {
        super(errorCode);
        this.data = data;
    }
}
