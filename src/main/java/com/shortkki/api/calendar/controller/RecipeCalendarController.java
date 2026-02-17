package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.controller.dto.request.CreateRecipeCalendarFromQueueRequest;
import com.shortkki.api.calendar.controller.dto.request.ReorderRecipeCalendarRequest;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarDetailResponse;
import com.shortkki.api.calendar.controller.dto.response.RecipeCalendarsResponse;
import com.shortkki.api.calendar.service.RecipeCalendarQueryService;
import com.shortkki.api.calendar.service.RecipeCalendarService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/calendar/recipes")
@RequiredArgsConstructor
public class RecipeCalendarController {

    private final RecipeCalendarService recipeCalendarService;
    private final RecipeCalendarQueryService recipeCalendarQueryService;

    @Operation(summary = "레시피 캘린더 목록 조회")
    @GetMapping
    public ResponseEntity<BaseResponse<RecipeCalendarsResponse>> getList(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        RecipeCalendarsResponse response = recipeCalendarQueryService
                .getRecipeCalendars(loginMember.getId(), startDate, endDate);
        return ResponseEntity.ok(BaseResponse.success("레시피 캘린더 목록을 조회했습니다.", response));
    }

    @Operation(summary = "대기열에서 레시피 캘린더 생성")
    @PostMapping
    public ResponseEntity<BaseResponse<RecipeCalendarDetailResponse>> createFromQueue(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody CreateRecipeCalendarFromQueueRequest request
    ) {
        RecipeCalendarDetailResponse response = recipeCalendarService.createFromQueue(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("대기열에서 레시피 캘린더에 등록되었습니다.", response));
    }

    @Operation(summary = "레시피 캘린더 목록 재정렬")
    @PatchMapping("/order")
    public ResponseEntity<BaseResponse<Void>> reorder(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody ReorderRecipeCalendarRequest request
    ) {
        recipeCalendarService.reorder(loginMember.getId(), request);
        return ResponseEntity.ok(BaseResponse.success("레시피 캘린더 순서가 변경되었습니다."));
    }

    @Operation(summary = "레시피 캘린더 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteCalendar(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        recipeCalendarService.deleteCalendar(loginMember.getId(), id);
        return ResponseEntity.ok(BaseResponse.success("레시피 캘린더가 삭제되었습니다."));
    }
}
