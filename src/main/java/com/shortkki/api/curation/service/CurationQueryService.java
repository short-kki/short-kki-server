package com.shortkki.api.curation.service;

import com.shortkki.api.curation.dto.response.CurationRecommendResponse;
import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.curation.repository.CurationRepository;
import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.recipe.entity.RecipeSource;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurationQueryService {

    private static final int CURATION_COUNT = 5;
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

    public List<CurationRecommendResponse> getRecommendedCurations(LocalDateTime now) {
        return getRecommendedCurations(now, jpaSearchPort);
    }

    public List<CurationRecommendResponse> getRecommendedCurationsV2(LocalDateTime now) {
        return getRecommendedCurations(now, esSearchPort);
    }

    private List<CurationRecommendResponse> getRecommendedCurations(
            LocalDateTime now, RecipeSearchPort searchPort
    ) {
        List<Curation> curations = getCurrentCurations(now, CURATION_COUNT);

        return curations.stream()
                .map(curation -> toCurationRecommendResponse(curation, searchPort))
                .toList();
    }

    private List<Curation> getCurrentCurations(LocalDateTime now, int size) {
        DayType dayType = DayType.from(now.getDayOfWeek());
        TimeType timeType = TimeType.from(now.toLocalTime());

        List<Curation> candidates = curationRepository.findMatchingCurations(
                dayType.name(), timeType.name(), size
        );

        Collections.shuffle(candidates);
        return candidates.size() <= size ? candidates : candidates.subList(0, size);
    }

    private CurationRecommendResponse toCurationRecommendResponse(
            Curation curation, RecipeSearchPort searchPort
    ) {
        List<RecipeSummaryResponse> recipes = searchPort.search(
                        PageRequest.of(0, RECIPES_PER_CURATION),
                        curation.getSearchWord(),
                        curation.getTags(),
                        curation.getIngredients(),
                        RecipeSource.IMPORT,
                        curation.getCuisineTypes(),
                        curation.getMealTypes(),
                        curation.getDifficulties()
                ).stream()
                .map(this::toRecipeSummaryResponse)
                .toList();

        return CurationRecommendResponse.builder()
                .curationId(curation.getId())
                .title(curation.getTitle())
                .description(curation.getDescription())
                .recipes(recipes)
                .build();
    }

    private RecipeSummaryResponse toRecipeSummaryResponse(RecipeSearchItem item) {
        return new RecipeSummaryResponse(
                item.id(),
                item.title(),
                item.bookmarkCount(),
                item.sourceUrl(),
                item.mainImgUrl(),
                item.recipeSource(),
                item.authorName(),
                item.authorProfileImgUrl(),
                item.platform(),
                item.creatorName(),
                item.creatorProfileImgUrl()
        );
    }
}
