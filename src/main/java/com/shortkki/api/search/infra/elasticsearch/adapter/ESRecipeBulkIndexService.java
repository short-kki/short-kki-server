package com.shortkki.api.search.infra.elasticsearch.adapter;

import com.shortkki.api.admin.controller.dto.ReindexResultResponse;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.elasticsearch.uris")
public class ESRecipeBulkIndexService {

    private final RecipeRepository recipeRepository;
    private final RecipeSearchIndexer recipeSearchIndexer;

    private static final int BATCH_SIZE = 100;

    @Transactional(readOnly = true)
    public ReindexResultResponse reindexAll() {
        List<Recipe> recipes = recipeRepository.findAll();

        if (recipes.isEmpty()) {
            log.info("인덱싱할 레시피가 없습니다.");
            return new ReindexResultResponse(0, 0, 0);
        }

        log.info("{}개 레시피 bulk 인덱싱 시작 (배치 크기={})", recipes.size(), BATCH_SIZE);

        int success = 0;
        for (int i = 0; i < recipes.size(); i += BATCH_SIZE) {
            List<Recipe> batch = recipes.subList(i, Math.min(i + BATCH_SIZE, recipes.size()));
            try {
                recipeSearchIndexer.upsertAll(batch);
                success += batch.size();
            } catch (Exception e) {
                log.warn("배치 인덱싱 실패 (offset={}): {}", i, e.getMessage());
            }
        }

        log.info("인덱싱 완료 (성공={}/{})", success, recipes.size());
        return new ReindexResultResponse(recipes.size(), success, recipes.size() - success);
    }
}
