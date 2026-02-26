package com.shortkki.api.auth.controller;

import com.shortkki.api.auth.application.service.AuthService;
import com.shortkki.api.auth.application.usecase.RegisterUserUseCase;
import com.shortkki.api.auth.dto.LoginRequest;
import com.shortkki.api.auth.dto.LoginResponse;
import com.shortkki.api.auth.dto.RefreshTokenRequest;
import com.shortkki.api.auth.dto.RefreshTokenResponse;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthService authService;

    @PostMapping("/{provider}")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @PathVariable OAuthProvider provider,
            @Valid @RequestBody LoginRequest request) {
        LoginResponse response = registerUserUseCase.execute(provider, request);
        return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", response));
    }

    // TODO : RT 로테이션 도입 및 레디스
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<RefreshTokenResponse>> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        RefreshTokenResponse response = authService.refreshAccessToken(
                request.accessToken(),
                request.refreshToken()
        );
        return ResponseEntity.ok(BaseResponse.success("토큰 재발급 성공", response));
    }


}
