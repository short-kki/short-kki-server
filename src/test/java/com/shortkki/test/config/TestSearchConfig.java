package com.shortkki.test.config;

import com.shortkki.api.search.application.port.RecipeSearchIndexer;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.SliceImpl;

@Configuration
public class TestSearchConfig {

    @Bean
    @Qualifier("esRecipeSearch")
    @ConditionalOnMissingBean(name = "ESRecipeSearchAdapter")
    public RecipeSearchPort noOpEsRecipeSearchPort() {
        return (pageable, searchWord, tags, ingredients, recipeSource, cuisineTypes, mealTypes, difficulties)
                -> new SliceImpl<>(List.of(), pageable, false);
    }

    @Bean
    @ConditionalOnMissingBean(RecipeSearchIndexer.class)
    public RecipeSearchIndexer noOpRecipeSearchIndexer() {
        return recipeId -> { };
    }
}
