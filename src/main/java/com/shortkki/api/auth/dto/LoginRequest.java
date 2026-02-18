package com.shortkki.api.auth.dto;

import com.shortkki.global.entity.Platform;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Authorization code는 필수입니다.")
    private String code;

    private String codeVerifier;

    private Platform platform;
}
