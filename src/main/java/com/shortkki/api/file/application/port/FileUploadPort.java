package com.shortkki.api.file.application.port;

import com.shortkki.api.file.application.dto.UploadUrlDto;

public interface FileUploadPort {

    UploadUrlDto createUploadUrl(String objectKey, String contentType, long expireSeconds);
}
