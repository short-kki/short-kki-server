package com.shortkki.api.recipeBook.service;

import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipeBook.dto.RecipeBookCreateRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookReorderRequest;
import com.shortkki.api.recipeBook.dto.RecipeBookResponse;
import com.shortkki.api.recipeBook.dto.RecipeBookUpdateRequest;
import com.shortkki.api.recipeBook.dto.RecipeSummaryResponse;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.entity.RecipeBookItem;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.group.entity.Group;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookService {

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final RecipeBookQueryService queryService;
    private final RecipeBookValidationService validationService;

    @Transactional
    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        Member member = validationService.findMemberById(memberId);

        List<RecipeBook> existingBooks = queryService.findAllByMemberId(memberId);
        int nextSortOrder = existingBooks.stream()
                .mapToInt(RecipeBook::getSortOrder)
                .max()
                .orElse(0) + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public List<RecipeBookResponse> findAllByMember(Long memberId) {
        List<RecipeBook> recipeBooks = queryService.findAllByMemberId(memberId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public List<RecipeBookResponse> findAllByGroup(Long memberId, Long groupId) {
        Group group = validationService.findGroupById(groupId);
        validationService.validateGroupMember(memberId, group);
        List<RecipeBook> recipeBooks = queryService.findAllByGroupId(groupId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public RecipeBookResponse findById(Long memberId, Long id) {
        RecipeBook recipeBook = validationService.findRecipeBookById(id);
        validationService.validateRecipeBookAccess(recipeBook, memberId);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookId(id);
        List<RecipeSummaryResponse> recipes = items.stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();

        return RecipeBookResponse.from(recipeBook, recipes);
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = validationService.findRecipeBookById(id);
        validationService.validateNotGroupRecipeBook(recipeBook);
        validationService.validateRecipeBookOwnership(recipeBook, memberId);

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        RecipeBook recipeBook = validationService.findRecipeBookById(id);
        validationService.validateNotGroupRecipeBook(recipeBook);
        validationService.validateRecipeBookOwnership(recipeBook, memberId);
        validationService.validateNotDefaultRecipeBook(recipeBook);

        recipeBookItemRepository.deleteAllByRecipeBookId(id);
        recipeBookRepository.delete(recipeBook);
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = validationService.findRecipeBookById(recipeBookId);
        validationService.validateRecipeBookAccess(recipeBook, memberId);

        Recipe recipe = validationService.findRecipeById(recipeId);
        validationService.validateRecipeNotInBook(recipeBookId, recipeId);

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);
    }

    @Transactional
    public void addRecipeIfNotExists(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = validationService.findRecipeBookById(recipeBookId);
        validationService.validateRecipeBookAccess(recipeBook, memberId);
        Recipe recipe = validationService.findRecipeById(recipeId);

        if (recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            return;
        }

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = validationService.findRecipeBookById(recipeBookId);
        validationService.validateRecipeBookAccess(recipeBook, memberId);
        validationService.validateRecipeInBook(recipeBookId, recipeId);

        recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId, recipeId);
    }

    @Transactional
    public void createDefaultForMember(Member member) {
        if (queryService.findDefaultByMemberId(member.getId()).isPresent()) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.create(member, "내 레시피북", true, 1);
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void createDefaultForMember(Long memberId) {
        Member member = validationService.findMemberById(memberId);
        createDefaultForMember(member);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        validationService.findGroupById(groupId);

        boolean hasDefault = queryService.findAllByGroupId(groupId).stream()
                .anyMatch(RecipeBook::getIsDefault);

        if (hasDefault) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.createForGroup(groupId, groupName + " 레시피북");
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void reorder(Long memberId, RecipeBookReorderRequest request) {
        List<Long> recipeBookIds = request.recipeBookIds();
        List<RecipeBook> memberRecipeBooks = queryService.findAllByMemberId(memberId);

        validationService.validateReorderRequest(recipeBookIds, memberRecipeBooks);

        Map<Long, RecipeBook> recipeBookMap = memberRecipeBooks.stream()
                .collect(Collectors.toMap(RecipeBook::getId, book -> book));

        for (int i = 0; i < recipeBookIds.size(); i++) {
            RecipeBook book = recipeBookMap.get(recipeBookIds.get(i));
            book.updateSortOrder(i + 1);
        }

        recipeBookRepository.saveAll(recipeBookMap.values());
    }
}
