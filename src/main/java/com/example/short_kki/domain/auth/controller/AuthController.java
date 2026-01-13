package com.example.short_kki.domain.auth.controller;

import com.example.short_kki.domain.auth.dto.LoginRequest;
import com.example.short_kki.domain.auth.dto.LoginResponse;
import com.example.short_kki.domain.auth.service.AuthService;
import com.example.short_kki.global.response.BaseResponse;
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

  private final AuthService authService;

  @PostMapping("/{provider}")
  public ResponseEntity<BaseResponse<LoginResponse>> login(
      @PathVariable String provider,
      @Valid @RequestBody LoginRequest request
  ) {
    LoginResponse response = authService.login(provider, request);
    return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", response));
  }
}
