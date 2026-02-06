package com.shortkki.api.search.infra.elasticsearch.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.elasticsearch.uris")
public class ESRecipeIndexLoader implements ApplicationRunner {

    private final RecipeRepository recipeRepository;
    private final ESRecipeSearchIndexer recipeSearchIndexer;
    private final ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "recipes";
    private static final int BATCH_SIZE = 100;

    @Override
    public void run(ApplicationArguments args) {
        recreateIndex();

        List<Recipe> recipes = recipeRepository.findAll();

        if (recipes.isEmpty()) {
            log.info("인덱싱할 레시피가 없습니다.");
            return;
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
    }

    private void recreateIndex() {
        try {
            boolean exists = elasticsearchClient.indices()
                    .exists(e -> e.index(INDEX_NAME))
                    .value();

            if (exists) {
                elasticsearchClient.indices().delete(d -> d.index(INDEX_NAME));
                log.info("기존 인덱스 삭제: {}", INDEX_NAME);
            }

            try (InputStream is = new ClassPathResource("elasticsearch/recipe-index.json").getInputStream()) {
                elasticsearchClient.indices().create(c -> c.index(INDEX_NAME).withJson(is));
            }
            log.info("인덱스 생성 완료: {}", INDEX_NAME);
        } catch (Exception e) {
            log.warn("인덱스 재생성 실패: {}", e.getMessage());
        }
    }
}
