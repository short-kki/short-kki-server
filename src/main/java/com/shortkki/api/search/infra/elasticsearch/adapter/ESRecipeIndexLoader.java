package com.shortkki.api.search.infra.elasticsearch.adapter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.elasticsearch.uris")
public class ESRecipeIndexLoader implements ApplicationRunner {

    private final ESRecipeBulkIndexService bulkIndexService;
    private final ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "recipes";

    @Override
    public void run(ApplicationArguments args) {
        recreateIndex();
        bulkIndexService.reindexAll();
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

            try (InputStream is = new ClassPathResource("search/recipe-index.json").getInputStream()) {
                elasticsearchClient.indices().create(c -> c.index(INDEX_NAME).withJson(is));
            }
            log.info("인덱스 생성 완료: {}", INDEX_NAME);
        } catch (Exception e) {
            log.warn("인덱스 재생성 실패: {}", e.getMessage());
        }
    }
}
