package com.shortkki.api.curation.service;

import com.shortkki.api.curation.controller.dto.response.CurationRecommendResponse;
import com.shortkki.api.curation.controller.dto.response.CurationRecommendsResponse;
import com.shortkki.api.curation.controller.dto.response.RecipeCurationSearchResponse;
import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.curation.repository.CurationRepository;
import com.shortkki.api.recipe.dto.response.RecipeSearchItemResponse;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.recipeBook.service.RecipeBookReadService;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
import com.shortkki.global.response.page.SlicePageInfoResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurationQueryServiceV2 {

    private static final int DEFAULT_CURATION_COUNT = 5;
    private static final int RECIPES_PER_CURATION = 10;

    private final CurationRepository curationRepository;
    private final RecipeSearchPort esSearchPort;
    private final RecipeBookReadService recipeBookReadService;

    public CurationQueryServiceV2(
            CurationRepository curationRepository,
            @Qualifier("esRecipeSearch") RecipeSearchPort esSearchPort,
            RecipeBookReadService recipeBookReadService
    ) {
        this.curationRepository = curationRepository;
        this.esSearchPort = esSearchPort;
        this.recipeBookReadService = recipeBookReadService;
    }

    public CurationRecommendsResponse getRecommendedCurations(Long memberId, LocalDateTime now, Pageable pageable) {
        Pageable limited = limitPageSize(pageable);
        Slice<Curation> curations = getCurrentCurations(now, limited);
        Pageable recipePageable = PageRequest.of(0, RECIPES_PER_CURATION);

        List<CurationRecommendResponse> items = curations.getContent().stream()
                .map(c -> toCurationRecommendResponse(memberId, c, search(c, recipePageable)))
                .toList();

        return new CurationRecommendsResponse(items, SlicePageInfoResponse.from(curations));
    }

    public RecipeCurationSearchResponse searchRecipesByCuration(Long memberId, long id, Pageable pageable) {
        Curation curation = findById(id);
        Slice<RecipeSearchItem> result = search(curation, pageable);
        return toRecipeCurationSearchResponse(memberId, curation, result);
    }

    public RecipeCurationSearchResponse searchTopCuration(Long memberId, Pageable pageable) {
        Slice<RecipeSearchItem> result = esSearchPort.searchForCuration(
                pageable, null, null, null,
                RecipeSource.IMPORT, null, null, null
        );

        return toRecipeCurationSearchResponse(memberId, null, result);
    }

    private Curation findById(long id) {
        return curationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CURATION_NOT_FOUND));
    }

    private Slice<RecipeSearchItem> search(Curation curation, Pageable pageable) {
        return esSearchPort.search(
                pageable, curation.getSearchWord(), curation.getTags(), curation.getIngredients(),
                RecipeSource.IMPORT, curation.getCuisineTypes(), curation.getMealTypes(), curation.getDifficulties()
        );
    }

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
            Long memberId, Curation curation, Slice<RecipeSearchItem> searchResult
    ) {
        List<RecipeSearchItemResponse> recipes = toSearchItemResponses(memberId, searchResult.getContent());

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
            Long memberId, Curation curation, Slice<RecipeSearchItem> searchResult
    ) {
        List<RecipeSearchItemResponse> recipes = toSearchItemResponses(memberId, searchResult.getContent());
        return new RecipeCurationSearchResponse(
                curation != null ? curation.getId() : null,
                recipes,
                SlicePageInfoResponse.from(searchResult)
        );
    }

    private List<RecipeSearchItemResponse> toSearchItemResponses(Long memberId, List<RecipeSearchItem> items) {
        List<Long> recipeIds = items.stream().map(RecipeSearchItem::id).toList();
        Set<Long> bookmarkedIds = recipeBookReadService.findBookmarkedRecipeIds(memberId, recipeIds);
        return items.stream()
                .map(item -> RecipeSearchItemResponse.from(item, bookmarkedIds.contains(item.id())))
                .toList();
    }
}
