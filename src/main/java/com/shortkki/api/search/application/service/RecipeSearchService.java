package com.shortkki.api.search.application.service;

import com.shortkki.api.recipe.dto.response.RecipeSearchItemResponse;
import com.shortkki.api.recipe.entity.CuisineType;
import com.shortkki.api.recipe.entity.Difficulty;
import com.shortkki.api.recipe.entity.MealType;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipeBook.service.RecipeBookReadService;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.api.search.controller.dto.RecipeSearchResponse;
import com.shortkki.api.search.util.SearchWordTokenizer;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.util.List;
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
    private final RecipeBookReadService recipeBookReadService;

    public RecipeSearchService(
            @Qualifier("jpaRecipeSearch") RecipeSearchPort jpaSearchPort,
            @Qualifier("esRecipeSearch") RecipeSearchPort esSearchPort,
            RecipeBookReadService recipeBookReadService
    ) {
        this.jpaSearchPort = jpaSearchPort;
        this.esSearchPort = esSearchPort;
        this.recipeBookReadService = recipeBookReadService;
    }

    public RecipeSearchResponse search(
            Long memberId, Pageable pageable, String searchWord, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        validateSearchWord(searchWord);
        Set<String> keywords = SearchWordTokenizer.tokenize(searchWord);
        Slice<RecipeSearchItem> result = jpaSearchPort.search(
                pageable, searchWord, keywords, keywords, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return toResponse(memberId, result);
    }

    public RecipeSearchResponse searchV2(
            Long memberId, Pageable pageable, String searchWord, RecipeSource recipeSource,
            Set<CuisineType> cuisineTypes, Set<MealType> mealTypes, Set<Difficulty> difficulties
    ) {
        Set<String> keywords = (searchWord != null && !searchWord.isBlank())
                ? SearchWordTokenizer.tokenize(searchWord)
                : Set.of();
        Slice<RecipeSearchItem> result = esSearchPort.search(
                pageable, searchWord, keywords, keywords, recipeSource, cuisineTypes, mealTypes, difficulties
        );
        return toResponse(memberId, result);
    }

    private RecipeSearchResponse toResponse(Long memberId, Slice<RecipeSearchItem> result) {
        List<RecipeSearchItem> items = result.getContent();
        List<Long> recipeIds = items.stream().map(RecipeSearchItem::id).toList();
        Set<Long> bookmarkedIds = recipeBookReadService.findBookmarkedRecipeIds(memberId, recipeIds);

        List<RecipeSearchItemResponse> searchResult = items.stream()
                .map(item -> RecipeSearchItemResponse.from(item, bookmarkedIds.contains(item.id())))
                .toList();
        return RecipeSearchResponse.builder()
                .searchResult(searchResult)
                .pageInfo(SlicePageInfoResponse.from(result))
                .build();
    }

    private static void validateSearchWord(String searchWord) {
        if (searchWord == null || searchWord.isBlank()) {
            throw new BadRequestException("검색어는 필수입니다.");
        }
    }
}
