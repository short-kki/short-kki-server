package com.shortkki.api.recipeImport.controller;

import com.shortkki.api.recipeImport.dto.RecipeImportRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportResponse;
import com.shortkki.api.recipeImport.service.RecipeImportService;
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
@RequestMapping("/api/v1/recipe-imports")
@RequiredArgsConstructor
public class RecipeImportController {

    private final RecipeImportService recipeImportService;

    @PostMapping
    public ResponseEntity<BaseResponse<RecipeImportResponse>> importRecipe(
            @AuthenticationPrincipal LoginMember loginMember,
            @Valid @RequestBody RecipeImportRequest request) {
        RecipeImportResponse response = recipeImportService.importFromUrl(loginMember.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("외부 레시피를 가져왔습니다.", response));
    }
}
