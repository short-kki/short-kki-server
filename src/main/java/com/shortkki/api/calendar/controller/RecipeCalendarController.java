package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarResponse;
import com.shortkki.api.calendar.service.RecipeCalendarService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calendar/recipes")
@RequiredArgsConstructor
public class RecipeCalendarController {

    private final RecipeCalendarService recipeCalendarService;

    @PostMapping
    public ResponseEntity<BaseResponse<RecipeCalendarResponse>> addFromQueue(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateRecipeCalendarFromQueueRequest request
    ) {
        RecipeCalendarResponse response = recipeCalendarService.createFromQueue(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("대기열에서 레시피 캘린더에 등록되었습니다.", response));
    }
}
