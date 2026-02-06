package com.shortkki.api.calendar.controller;

import com.shortkki.api.calendar.service.RecipeCalendarService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/calendar/recipes")
@RequiredArgsConstructor
public class GroupRecipeCalendarController {

    private final RecipeCalendarService recipeCalendarService;

    @Operation(summary = "그룹 레시피 캘린더 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteGroupCalendar(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long groupId,
            @PathVariable Long id
    ) {
        recipeCalendarService.deleteGroupCalendar(loginMember.getId(), groupId, id);
        return ResponseEntity.ok(BaseResponse.success("그룹 레시피 캘린더가 삭제되었습니다."));
    }
}
