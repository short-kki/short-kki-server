package com.shortkki.api.file.service.port;

import com.shortkki.api.file.service.dto.UploadUrlDto;

public interface FileUploadPort {

    UploadUrlDto createUploadUrl(String objectKey, String contentType, long expireSeconds);
}
