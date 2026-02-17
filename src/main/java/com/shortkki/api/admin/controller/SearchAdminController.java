package com.shortkki.api.admin.controller;

import com.shortkki.api.admin.controller.dto.ReindexRequest;
import com.shortkki.api.admin.controller.dto.ReindexResultResponse;
import com.shortkki.api.search.application.service.RecipeIndexService;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin")
@RestController
@RequestMapping("/api/admin/search")
@RequiredArgsConstructor
public class SearchAdminController {

    private final RecipeIndexService recipeIndexService;

    @PostMapping("/reindex")
    public ResponseEntity<BaseResponse<ReindexResultResponse>> reindex(@RequestBody ReindexRequest request) {
        ReindexResultResponse result = recipeIndexService.reindex(request);
        return ResponseEntity.ok(BaseResponse.success("재인덱싱이 완료되었습니다.", result));
    }
}
