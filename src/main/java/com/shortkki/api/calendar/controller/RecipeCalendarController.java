package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarsResponse;
import com.shortkki.api.calendar.service.RecipeCalendarQueryService;
import com.shortkki.api.calendar.service.RecipeCalendarService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/calendar/recipes")
@RequiredArgsConstructor
public class RecipeCalendarController {

    private final RecipeCalendarService recipeCalendarService;
    private final RecipeCalendarQueryService recipeCalendarQueryService;

    @GetMapping
    public ResponseEntity<BaseResponse<RecipeCalendarsResponse>> getList(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false) Long groupId
    ) {
        RecipeCalendarsResponse response = recipeCalendarQueryService
                .getRecipeCalendars(loginMember.getId(), groupId, startDate, endDate);
        return ResponseEntity.ok(BaseResponse.success("레시피 캘린더 목록을 조회했습니다.", response));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<RecipeCalendarDetailResponse>> createFromQueue(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateRecipeCalendarFromQueueRequest request
    ) {
        RecipeCalendarDetailResponse response = recipeCalendarService.createFromQueue(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("대기열에서 레시피 캘린더에 등록되었습니다.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        recipeCalendarService.delete(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success("레시피 캘린더가 삭제되었습니다."));
    }
}
