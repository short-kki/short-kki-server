package com.shortkki.api.search.infra.elasticsearch.repository;

import com.shortkki.api.search.infra.elasticsearch.document.RecipeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RecipeDocumentRepository extends ElasticsearchRepository<RecipeDocument, Long> {

}
