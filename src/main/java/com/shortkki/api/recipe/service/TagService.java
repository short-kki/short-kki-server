package com.shortkki.api.recipe.service;

import com.shortkki.api.recipe.entity.RecipeTag;
import com.shortkki.api.recipe.entity.Tag;
import com.shortkki.api.recipe.entity.TagSource;
import com.shortkki.api.recipe.repository.RecipeTagRepository;
import com.shortkki.api.recipe.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private static final int OFFICIAL_TAG_PROMPT_LIMIT = 200;

    private final TagRepository tagRepository;
    private final RecipeTagRepository recipeTagRepository;

    /**
     * 기존 tag가 있으면 재사용 (sourceType 변경 안 함). 없으면 주어진 source로 신규 생성. DataIntegrityViolationException은
     * 동시성 경합 대비.
     */
    public Tag getOrCreateTag(String name, TagSource source) {
        try {
            return tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Tag newTag = (source == TagSource.SYSTEM)
                                ? Tag.createSystemTag(name)
                                : Tag.createUserTag(name);
                        return tagRepository.save(newTag);
                    });
        } catch (DataIntegrityViolationException e) {
            return tagRepository.findByName(name).orElseThrow(() -> e);
        }
    }
    
    public void saveRecipeTags(Long recipeId, List<String> tagNames, TagSource source) {
        List<String> normalized = normalize(tagNames);
        if (normalized.isEmpty()) {
            return;
        }

        List<RecipeTag> recipeTags = new ArrayList<>(normalized.size());
        for (String name : normalized) {
            Tag tag = getOrCreateTag(name, source);
            recipeTags.add(RecipeTag.of(recipeId, tag.getId()));
        }

        recipeTagRepository.saveAll(recipeTags);
    }

    public List<String> findSystemTagNames() {
        return tagRepository.findAllBySourceTypeOrderByNameAsc(TagSource.SYSTEM).stream()
                .map(Tag::getName)
                .limit(OFFICIAL_TAG_PROMPT_LIMIT)
                .toList();
    }

    private List<String> normalize(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return List.of();
        }
        return tags.stream()
                .filter(t -> t != null && !t.isBlank())
                .map(String::trim)
                .distinct()
                .toList();
    }
}
