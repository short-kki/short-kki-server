package com.shortkki.api.search.infra.elasticsearch.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.shortkki.api.search.application.port.RecipeIndexManager;
import com.shortkki.api.search.config.ElasticsearchIndexProperties;
import com.shortkki.api.search.config.ElasticsearchIndexProperties.IndexConfig;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ESRecipeIndexManagerAdapter implements RecipeIndexManager {

    private final ElasticsearchClient elasticsearchClient;
    private final ElasticsearchIndexProperties indexProperties;

    @Override
    public void resetIndex() {
        IndexConfig config = indexProperties.index().get("recipe");
        String indexName = config.name();
        String settingsPath = config.settingsPath();

        try {
            boolean exists = elasticsearchClient.indices()
                    .exists(e -> e.index(indexName))
                    .value();

            if (exists) {
                elasticsearchClient.indices().delete(d -> d.index(indexName));
                log.info("기존 인덱스 삭제: {}", indexName);
            }

            try (InputStream is = new ClassPathResource(settingsPath).getInputStream()) {
                elasticsearchClient.indices().create(c -> c.index(indexName).withJson(is));
            }
            log.info("인덱스 생성 완료: {}", indexName);
        } catch (IOException e) {
            throw new RuntimeException("인덱스 초기화 실패: " + indexName, e);
        }
    }
}
