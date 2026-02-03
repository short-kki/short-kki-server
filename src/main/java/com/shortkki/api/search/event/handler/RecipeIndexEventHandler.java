package com.shortkki.api.search.event.handler;

import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import com.shortkki.api.search.event.RecipeIndexUpsertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeIndexEventHandler {

    private final RecipeSearchIndexer indexer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(RecipeIndexUpsertEvent event) {
        try {
            indexer.upsert(event.recipeId());
        } catch (Exception e) {
            log.error("ES indexing failed. recipeId={}", event.recipeId(), e);
        }
    }
}
