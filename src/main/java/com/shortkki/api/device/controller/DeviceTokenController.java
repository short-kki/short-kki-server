package com.shortkki.api.device.controller;

import com.shortkki.api.device.dto.request.DeleteDeviceTokenRequest;
import com.shortkki.api.device.dto.request.RegisterDeviceTokenRequest;
import com.shortkki.api.device.service.DeviceTokenService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/device-tokens")
@RequiredArgsConstructor
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> registerToken(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RegisterDeviceTokenRequest request
    ) {
        deviceTokenService.registerToken(
                loginMember.getId(),
                request.token(),
                request.deviceType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteToken(
            @Valid @RequestBody DeleteDeviceTokenRequest request
    ) {
        deviceTokenService.deleteToken(request.token());
        return ResponseEntity.ok(BaseResponse.success());
    }
}
