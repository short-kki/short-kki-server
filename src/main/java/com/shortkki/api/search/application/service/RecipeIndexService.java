package com.shortkki.api.search.application.service;

import com.shortkki.api.admin.controller.dto.ReindexRequest;
import com.shortkki.api.admin.controller.dto.ReindexResultResponse;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.search.application.port.RecipeIndexManager;
import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeIndexService {

    private final RecipeRepository recipeRepository;
    private final RecipeSearchIndexer recipeSearchIndexer;
    private final RecipeIndexManager recipeIndexManager;

    private static final int BATCH_SIZE = 100;

    @Transactional(readOnly = true)
    public ReindexResultResponse reindex(ReindexRequest request) {
        if (request.resetIndex()) {
            recipeIndexManager.resetIndex();
        }

        LocalDateTime fromCreatedAt = request.fromCreatedAt() != null
                ? request.fromCreatedAt().atStartOfDay()
                : null;

        return bulkIndex(request.fromId(), fromCreatedAt);
    }

    @Transactional(readOnly = true)
    public ReindexResultResponse reindexAll() {
        return bulkIndex(null, null);
    }

    public void resetIndex() {
        recipeIndexManager.resetIndex();
    }

    private ReindexResultResponse bulkIndex(Long fromId, LocalDateTime fromCreatedAt) {
        log.info("bulk 인덱싱 시작 (배치 크기={}, fromId={}, fromCreatedAt={})", BATCH_SIZE, fromId, fromCreatedAt);

        int totalCount = 0;
        int successCount = 0;
        Pageable pageable = PageRequest.of(0, BATCH_SIZE);

        Slice<Recipe> slice;
        do {
            slice = recipeRepository.findAllFrom(fromId, fromCreatedAt, pageable);
            List<Recipe> batch = slice.getContent();
            totalCount += batch.size();

            try {
                recipeSearchIndexer.upsertAll(batch);
                successCount += batch.size();
            } catch (Exception e) {
                log.warn("배치 인덱싱 실패 (page={}): {}", pageable.getPageNumber(), e.getMessage());
            }

            pageable = pageable.next();
        } while (slice.hasNext());

        log.info("인덱싱 완료 (성공={}/{})", successCount, totalCount);
        return new ReindexResultResponse(totalCount, successCount, totalCount - successCount);
    }
}
