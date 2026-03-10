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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurationQueryService {

    private static final int DEFAULT_CURATION_COUNT = 5;
    private static final int RECIPES_PER_CURATION = 10;

    private final CurationRepository curationRepository;
    private final RecipeSearchPort jpaSearchPort;
    private final RecipeBookReadService recipeBookReadService;

    public CurationQueryService(
            CurationRepository curationRepository,
            @Qualifier("jpaRecipeSearch") RecipeSearchPort jpaSearchPort,
            RecipeBookReadService recipeBookReadService
    ) {
        this.curationRepository = curationRepository;
        this.jpaSearchPort = jpaSearchPort;
        this.recipeBookReadService = recipeBookReadService;
    }

    public CurationRecommendsResponse getRecommendedCurations(Long memberId, LocalDateTime now, Pageable pageable) {
        Pageable limited = limitPageSize(pageable);
        Slice<Curation> curations = getShuffledCurations(now, limited);
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

    private Pageable limitPageSize(Pageable pageable) {
        if (pageable.getPageSize() > DEFAULT_CURATION_COUNT) {
            return PageRequest.of(pageable.getPageNumber(), DEFAULT_CURATION_COUNT);
        }
        return pageable;
    }

    private Slice<Curation> getShuffledCurations(LocalDateTime now, Pageable pageable) {
        DayType dayType = DayType.from(now.getDayOfWeek());
        TimeType timeType = TimeType.from(now.toLocalTime());

        // 조건에 맞는 모든 큐레이션 쿼리 후 섞기
        List<Curation> all = new ArrayList<>(
                curationRepository.findAllMatchingCurations(dayType.name(), timeType.name()));
        Collections.shuffle(all, new Random(now.toLocalDate().toEpochDay()));

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int fromIndex = page * size;

        // 범위를 넘으면,
        if (fromIndex >= all.size()) {
            return new SliceImpl<>(List.of(), pageable, false);
        }

        // 범위 내면,
        int toIndex = Math.min(fromIndex + size, all.size());
        return new SliceImpl<>(all.subList(fromIndex, toIndex), pageable, toIndex < all.size());
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
        return new RecipeCurationSearchResponse(curation.getId(), recipes, SlicePageInfoResponse.from(searchResult));
    }

    private List<RecipeSearchItemResponse> toSearchItemResponses(Long memberId, List<RecipeSearchItem> items) {
        List<Long> recipeIds = items.stream().map(RecipeSearchItem::id).toList();
        Set<Long> bookmarkedIds = recipeBookReadService.findBookmarkedRecipeIds(memberId, recipeIds);
        return items.stream()
                .map(item -> RecipeSearchItemResponse.from(item, bookmarkedIds.contains(item.id())))
                .toList();
    }
}
