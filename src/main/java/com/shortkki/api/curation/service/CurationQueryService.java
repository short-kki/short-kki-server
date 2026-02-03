package com.shortkki.api.curation.service;

import com.shortkki.api.curation.dto.response.CurationRecommendResponse;
import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.entity.DayType;
import com.shortkki.api.curation.entity.TimeType;
import com.shortkki.api.curation.repository.CurationRepository;
import com.shortkki.api.recipe.dto.response.RecipeSummaryResponse;
import com.shortkki.api.search.application.port.RecipeSearchPort;
import com.shortkki.api.search.application.port.dto.RecipeSearchItem;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurationQueryService {

    private static final int CURATION_COUNT = 5;
    private static final int RECIPES_PER_CURATION = 10;

    private final CurationRepository curationRepository;
    private final RecipeSearchPort recipeSearchPort;

    public List<CurationRecommendResponse> getRecommendedCurations(LocalDateTime now) {
        List<Curation> curations = getCurrentCurations(now, CURATION_COUNT);

        return curations.stream()
                .map(this::toCurationRecommendResponse)
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

    private CurationRecommendResponse toCurationRecommendResponse(Curation curation) {
        String searchWord = mergeKeywordsToSearchWord(curation);

        List<RecipeSummaryResponse> recipes = recipeSearchPort.search(
                        PageRequest.of(0, RECIPES_PER_CURATION),
                        searchWord,
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
        return RecipeSummaryResponse.builder()
                .id(item.id())
                .title(item.title())
                .bookmarkCount(item.bookmarkCount())
                .thumbnailUrl(item.thumbnailUrl())
                .authorName(item.authorName())
                .build();
    }

    private String mergeKeywordsToSearchWord(Curation curation) {
        Set<String> merged = new HashSet<>();
        addAll(merged, curation.getKeywords());
        addAll(merged, curation.getTags());
        addAll(merged, curation.getIngredients());
        return String.join(" ", merged);
    }

    private void addAll(Set<String> target, Set<String> source) {
        if (source != null) {
            target.addAll(source);
        }
    }
}
