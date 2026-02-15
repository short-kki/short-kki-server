package com.shortkki.api.curation.service;

import com.shortkki.api.curation.controller.dto.response.CurationRecommendResponse;
import com.shortkki.api.curation.controller.dto.response.CurationRecommendsResponse;
import com.shortkki.api.curation.controller.dto.response.RecipeCurationSearchResponse;
import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.curation.repository.CurationRepository;
import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurationQueryService {

    private static final int DEFAULT_CURATION_COUNT = 5;
    private static final int RECIPES_PER_CURATION = 10;

    private final CurationRepository curationRepository;
    private final RecipeSearchPort jpaSearchPort;
    private final RecipeSearchPort esSearchPort;

    public CurationQueryService(
            CurationRepository curationRepository,
            @Qualifier("jpaRecipeSearch") RecipeSearchPort jpaSearchPort,
            @Qualifier("esRecipeSearch") RecipeSearchPort esSearchPort
    ) {
        this.curationRepository = curationRepository;
        this.jpaSearchPort = jpaSearchPort;
        this.esSearchPort = esSearchPort;
    }

    public CurationRecommendsResponse getRecommendedCurations(LocalDateTime now, Pageable pageable) {
        Pageable limited = limitPageSize(pageable);
        Slice<Curation> curations = getCurrentCurations(now, limited);
        Pageable recipePageable = PageRequest.of(0, RECIPES_PER_CURATION);

        List<CurationRecommendResponse> items = curations.getContent().stream()
                .map(c -> toCurationRecommendResponse(c, search(c, recipePageable)))
                .toList();

        return new CurationRecommendsResponse(items, SlicePageInfoResponse.from(curations));
    }

    public CurationRecommendsResponse getRecommendedCurationsV2(LocalDateTime now, Pageable pageable) {
        Pageable limited = limitPageSize(pageable);
        Slice<Curation> curations = getCurrentCurations(now, limited);
        Pageable recipePageable = PageRequest.of(0, RECIPES_PER_CURATION);

        List<CurationRecommendResponse> items = curations.getContent().stream()
                .map(c -> toCurationRecommendResponse(c, searchV2(c, recipePageable)))
                .toList();

        return new CurationRecommendsResponse(items, SlicePageInfoResponse.from(curations));
    }

    public RecipeCurationSearchResponse searchRecipesByCuration(long id, Pageable pageable) {
        Curation curation = findById(id);
        Slice<RecipeSearchItem> result = search(curation, pageable);
        return toRecipeCurationSearchResponse(curation, result);
    }

    public RecipeCurationSearchResponse searchRecipesByCurationV2(long id, Pageable pageable) {
        Curation curation = findById(id);
        Slice<RecipeSearchItem> result = searchV2(curation, pageable);
        return toRecipeCurationSearchResponse(curation, result);
    }

    public Curation findById(long id) {
        return curationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CURATION_NOT_FOUND));
    }

    private Slice<RecipeSearchItem> search(Curation curation, Pageable pageable) {
        return jpaSearchPort.search(
                pageable, curation.getSearchWord(), curation.getTags(), curation.getIngredients(),
                RecipeSource.IMPORT, curation.getCuisineTypes(), curation.getMealTypes(), curation.getDifficulties()
        );
    }

    private Slice<RecipeSearchItem> searchV2(Curation curation, Pageable pageable) {
        return esSearchPort.searchForCuration(
                pageable, curation.getSearchWord(), curation.getTags(), curation.getIngredients(),
                RecipeSource.IMPORT, curation.getCuisineTypes(), curation.getMealTypes(), curation.getDifficulties()
        );
    }

    /**
     * 응답 변환 헬퍼
     */
    private Pageable limitPageSize(Pageable pageable) {
        if (pageable.getPageSize() > DEFAULT_CURATION_COUNT) {
            return PageRequest.of(pageable.getPageNumber(), DEFAULT_CURATION_COUNT);
        }
        return pageable;
    }

    private Slice<Curation> getCurrentCurations(LocalDateTime now, Pageable pageable) {
        DayType dayType = DayType.from(now.getDayOfWeek());
        TimeType timeType = TimeType.from(now.toLocalTime());
        return curationRepository.findMatchingCurations(dayType.name(), timeType.name(), pageable);
    }

    private CurationRecommendResponse toCurationRecommendResponse(
            Curation curation, Slice<RecipeSearchItem> searchResult
    ) {
        List<RecipeSummaryResponse> recipes = searchResult.stream()
                .map(RecipeSummaryResponse::from)
                .toList();

        return CurationRecommendResponse.builder()
                .curationId(curation.getId())
                .title(curation.getTitle())
                .description(curation.getDescription())
                .mealTypes(curation.getMealTypes())
                .cuisineTypes(curation.getCuisineTypes())
                .recipes(recipes)
                .pageInfo(SlicePageInfoResponse.from(searchResult))
                .build();
    }

    private RecipeCurationSearchResponse toRecipeCurationSearchResponse(
            Curation curation, Slice<RecipeSearchItem> searchResult
    ) {
        List<RecipeSummaryResponse> recipes = searchResult.stream()
                .map(RecipeSummaryResponse::from)
                .toList();

        return new RecipeCurationSearchResponse(curation.getId(), recipes, SlicePageInfoResponse.from(searchResult));
    }
}
