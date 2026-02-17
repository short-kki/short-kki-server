package com.shortkki.api.search.infra.elasticsearch.adapter;

import com.shortkki.api.search.application.service.RecipeIndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class ESRecipeIndexLoader implements ApplicationRunner {

    private final RecipeIndexService recipeIndexService;

    @Override
    public void run(ApplicationArguments args) {
        recipeIndexService.resetIndex();
        recipeIndexService.reindexAll();
    }
}
