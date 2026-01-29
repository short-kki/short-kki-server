package com.shortkki.api.search.service;

import com.shortkki.api.recipe.dto.response.RecipeSearchResponse;
import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.search.repository.RecipeSearchRepository;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecipeSearchService {

    private static final Pattern TOKEN_DELIMITER = Pattern.compile("[\\s\\p{Punct}]+");

    private final RecipeSearchRepository recipeSearchRepository;

    public RecipeSearchResponse search(
            String searchWord, List<CuisineType> cuisineTypes, List<MealType> mealTypes, Pageable pageable
    ) {
        Set<String> keywords = parseKeywords(searchWord);

        Slice<Recipe> recipes = recipeSearchRepository.search(
                keywords, cuisineTypes, mealTypes, pageable
        );

        return RecipeSearchResponse.builder()
                .searchResult(
                        recipes.stream()
                                .map(RecipeSummaryResponse::from)
                                .toList()
                )
                .pageInfo(SlicePageInfoResponse.from(recipes))
                .build();
    }

    private Set<String> parseKeywords(String searchWord) {
        if (searchWord == null) {
            return Set.of();
        }

        String s = searchWord.trim();
        if (s.isEmpty()) {
            return Set.of();
        }

        return TOKEN_DELIMITER.splitAsStream(s)
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toUnmodifiableSet());
    }
}
