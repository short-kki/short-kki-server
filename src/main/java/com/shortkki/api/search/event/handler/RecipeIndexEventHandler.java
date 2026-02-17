package com.shortkki.api.search.event.handler;

import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import com.shortkki.api.search.event.RecipeIndexBulkUpsertEvent;
import com.shortkki.api.search.event.RecipeIndexDeleteEvent;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeIndexEventHandler {

    private final RecipeSearchIndexer indexer;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(RecipeIndexUpsertEvent event) {
        try {
            indexer.upsert(event.recipeId());
            log.info("ES indexing success. recipeId={}", event.recipeId());
        } catch (Exception e) {
            log.error("ES indexing failed. recipeId={}", event.recipeId(), e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(RecipeIndexBulkUpsertEvent event) {
        try {
            event.recipeIds().forEach(indexer::upsert);
            log.info("ES bulk indexing success. recipeIds={}", event.recipeIds());
        } catch (Exception e) {
            log.error("ES bulk indexing failed. recipeIds={}", event.recipeIds(), e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(RecipeIndexDeleteEvent event) {
        try {
            indexer.delete(event.recipeId());
            log.info("ES index delete success. recipeId={}", event.recipeId());
        } catch (Exception e) {
            log.error("ES index delete failed. recipeId={}", event.recipeId(), e);
        }
    }
}
