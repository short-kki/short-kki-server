package com.shortkki.api.notification.controller;

import com.shortkki.api.notification.controller.dto.FcmTokenDeleteRequest;
import com.shortkki.api.notification.controller.dto.FcmTokenRegisterRequest;
import com.shortkki.api.notification.controller.dto.NotificationSliceResponse;
import com.shortkki.api.notification.controller.dto.UnreadCountResponse;
import com.shortkki.api.notification.entity.Notification;
import com.shortkki.api.notification.service.NotificationService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification", description = "알림 API")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "FCM 토큰 등록")
    @PostMapping("/fcm-token")
    public BaseResponse<Void> registerFcmToken(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody FcmTokenRegisterRequest request
    ) {
        notificationService.registerFcmToken(
                loginMember.getId(),
                request.fcmToken(),
                request.deviceId(),
                request.platform()
        );
        return BaseResponse.success();
    }

    @Operation(summary = "FCM 토큰 해제")
    @DeleteMapping("/fcm-token")
    public BaseResponse<Void> deleteFcmToken(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody FcmTokenDeleteRequest request
    ) {
        notificationService.deleteFcmToken(loginMember.getId(), request.fcmToken());
        return BaseResponse.success();
    }

    @Operation(summary = "알림 목록 조회 (커서 기반 페이징)")
    @GetMapping
    public BaseResponse<NotificationSliceResponse> getNotifications(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        Slice<Notification> notifications = notificationService.getNotifications(
                loginMember.getId(), cursor, size);
        return BaseResponse.success(NotificationSliceResponse.from(notifications));
    }

    @Operation(summary = "읽지 않은 알림 수 조회")
    @GetMapping("/unread-count")
    public BaseResponse<UnreadCountResponse> getUnreadCount(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        long count = notificationService.getUnreadCount(loginMember.getId());
        return BaseResponse.success(UnreadCountResponse.of(count));
    }

    @Operation(summary = "알림 읽음 처리")
    @PatchMapping("/{id}/read")
    public BaseResponse<Void> markAsRead(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        notificationService.markAsRead(loginMember.getId(), id);
        return BaseResponse.success();
    }

    @Operation(summary = "전체 알림 읽음 처리")
    @PatchMapping("/read-all")
    public BaseResponse<Void> markAllAsRead(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        notificationService.markAllAsRead(loginMember.getId());
        return BaseResponse.success();
    }
}
