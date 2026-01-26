package com.shortkki.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String email;
    private final String name;
    private final boolean isNewMember;
}
