package com.shortkki.api.auth.dto;


public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {

}
