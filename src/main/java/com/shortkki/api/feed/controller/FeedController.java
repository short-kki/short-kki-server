package com.shortkki.api.feed.controller;

import com.shortkki.api.feed.dto.request.CreateFeedRequest;
import com.shortkki.api.feed.dto.response.FeedResponse;
import com.shortkki.api.feed.service.FeedService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<FeedResponse>>> getGroupFeeds(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId
    ) {
        List<FeedResponse> response = feedService.getGroupFeeds(loginMember.getId(), groupId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> createFeed(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @Valid @RequestBody CreateFeedRequest request
    ) {
        feedService.createFeed(loginMember.getId(), groupId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success());
    }
}
