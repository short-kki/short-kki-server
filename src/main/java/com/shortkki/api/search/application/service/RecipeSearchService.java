package com.shortkki.api.search.application.service;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.controller.dto.RecipeSearchResponse;
import com.shortkki.api.search.util.SearchWordTokenizer;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecipeSearchService {

    private final RecipeSearchPort jpaSearchPort;
    private final RecipeSearchPort esSearchPort;

    public RecipeSearchService(
            @Qualifier("jpaRecipeSearch") RecipeSearchPort jpaSearchPort,
            @Qualifier("esRecipeSearch") RecipeSearchPort esSearchPort
    ) {
        this.jpaSearchPort = jpaSearchPort;
        this.esSearchPort = esSearchPort;
    }

    public RecipeSearchResponse search(
            Pageable pageable, String searchWord, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        validateSearchWord(searchWord);
        Set<String> keywords = SearchWordTokenizer.tokenize(searchWord);
        Slice<RecipeSearchItem> result = jpaSearchPort.search(
                pageable, searchWord, keywords, keywords, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return RecipeSearchResponse.from(result);
    }

    public RecipeSearchResponse searchV2(
            Pageable pageable, String searchWord, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        Set<String> keywords = (searchWord != null && !searchWord.isBlank())
                ? SearchWordTokenizer.tokenize(searchWord)
                : Set.of();
        Slice<RecipeSearchItem> result = esSearchPort.search(
                pageable, searchWord, keywords, keywords, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return RecipeSearchResponse.from(result);
    }

    private static void validateSearchWord(String searchWord) {
        if (searchWord == null || searchWord.isBlank()) {
            throw new BadRequestException("검색어는 필수입니다.");
        }
    }
}
