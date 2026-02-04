package com.shortkki.api.source.controller;

import com.shortkki.api.recipeImport.dto.RecipeImportPreviewResponse;
import com.shortkki.api.source.service.SourceContentQueryService;
import com.shortkki.global.auth.dto.LoginMember;
import com.shortkki.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/source/preview")
public class SourceContentController {

    private final SourceContentQueryService sourceContentQueryService;

    @GetMapping
    public ResponseEntity<BaseResponse<RecipeImportPreviewResponse>> getContentSourcePreview(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestParam String url
    ) {
        RecipeImportPreviewResponse response = sourceContentQueryService.getSourceContentPreview(url);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }
}
