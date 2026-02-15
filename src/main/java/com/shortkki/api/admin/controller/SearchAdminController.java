package com.shortkki.api.admin.controller;

import com.shortkki.api.admin.controller.dto.ReindexResultResponse;
import com.shortkki.api.search.infra.elasticsearch.adapter.ESRecipeBulkIndexService;
import com.shortkki.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin")
@RestController
@RequestMapping("/api/admin/search")
@RequiredArgsConstructor
public class SearchAdminController {

    private final ESRecipeBulkIndexService bulkIndexService;

    @PostMapping("/reindex")
    public ResponseEntity<BaseResponse<ReindexResultResponse>> reindex() {
        ReindexResultResponse result = bulkIndexService.reindexAll();
        return ResponseEntity.ok(BaseResponse.success("재인덱싱이 완료되었습니다.", result));
    }
}
