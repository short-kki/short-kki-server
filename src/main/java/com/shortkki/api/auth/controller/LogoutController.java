package com.shortkki.api.auth.controller;

import com.shortkki.api.auth.application.service.AuthService;
import com.shortkki.api.auth.dto.LogoutRequest;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LogoutController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody LogoutRequest request
    ) {
        authService.logout(loginMember.getId(), loginMember.getJti(), request.refreshToken());
        return ResponseEntity.ok(BaseResponse.success("로그아웃 성공", null));
    }
}