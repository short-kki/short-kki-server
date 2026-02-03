package com.shortkki.api.search.application.service;

import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.controller.dto.RecipeSearchResponse;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecipeSearchService {

    private final RecipeSearchPort recipeSearchPort;

    public RecipeSearchResponse search(
            Pageable pageable, String searchWord,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        validateSearchWord(searchWord);
        Slice<RecipeSearchItem> result = recipeSearchPort.search(
                pageable, searchWord, cuisineTypes, mealTypes, difficulties
        );
        return RecipeSearchResponse.from(result);
    }

    private static void validateSearchWord(String searchWord) {
        if (searchWord == null || searchWord.isBlank()) {
            throw new BadRequestException("검색어는 필수입니다.");
        }
    }
}
