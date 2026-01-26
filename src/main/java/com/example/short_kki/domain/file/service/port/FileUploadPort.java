package com.example.short_kki.domain.file.service.port;

import com.example.short_kki.domain.file.service.dto.UploadUrlDto;

public interface FileUploadPort {

    UploadUrlDto createUploadUrl(String objectKey, String contentType, long expireSeconds);
}
