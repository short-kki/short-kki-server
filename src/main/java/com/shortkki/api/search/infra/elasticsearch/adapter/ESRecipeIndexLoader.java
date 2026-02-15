package com.shortkki.api.search.infra.elasticsearch.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.shortkki.api.search.config.ElasticsearchIndexProperties;
import com.shortkki.api.search.config.ElasticsearchIndexProperties.IndexConfig;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class ESRecipeIndexLoader implements ApplicationRunner {

    private final ESRecipeBulkIndexService bulkIndexService;
    private final ElasticsearchClient elasticsearchClient;
    private final ElasticsearchIndexProperties indexProperties;

    @Override
    public void run(ApplicationArguments args) {
        IndexConfig config = indexProperties.index().get("recipe");
        recreateIndex(config.name(), config.settingsPath());
        bulkIndexService.reindexAll();
    }

    private void recreateIndex(String indexName, String settingsPath) {
        try {
            boolean exists = elasticsearchClient.indices()
                    .exists(e -> e.index(indexName))
                    .value();

            if (exists) {
                elasticsearchClient.indices().delete(d -> d.index(indexName));
                log.info("기존 인덱스 삭제: {}", indexName);
            }

            try (InputStream is = new FileSystemResource(settingsPath).getInputStream()) {
                elasticsearchClient.indices().create(c -> c.index(indexName).withJson(is));
            }
            log.info("인덱스 생성 완료: {}", indexName);
        } catch (Exception e) {
            log.warn("인덱스 재생성 실패: {}", e.getMessage());
        }
    }
}
