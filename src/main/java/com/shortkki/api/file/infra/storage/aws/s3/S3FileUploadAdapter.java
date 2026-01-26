package com.shortkki.api.file.infra.storage.aws.s3;

import com.shortkki.api.file.service.dto.UploadUrlDto;
import com.shortkki.api.file.service.port.FileUploadPort;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3FileUploadAdapter implements FileUploadPort {

    private final S3Presigner presigner;

    @Value("${aws.service.s3.bucket}")
    private String bucket;

    @Override
    public UploadUrlDto createUploadUrl(String objectKey, String contentType, long expireSeconds) {
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignReq = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(expireSeconds))
                .putObjectRequest(put)
                .build();

        PresignedPutObjectRequest signed = presigner.presignPutObject(presignReq);

        Map<String, String> headers = new HashMap<>();
        signed.signedHeaders().forEach((k, v) -> headers.put(k, String.join(",", v)));

        return UploadUrlDto.builder()
                .objectKey(objectKey)
                .uploadUrl(signed.url().toString())
                .method("PUT")
                .headers(headers)
                .expiresAt(Instant.now().plusSeconds(expireSeconds))
                .build();
    }
}
