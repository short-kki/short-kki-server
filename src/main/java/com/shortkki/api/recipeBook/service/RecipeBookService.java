package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
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
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.NotFoundException;
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

    private final RecipeBookQueryService recipeBookQueryService;
    private final RecipeBookValidationService recipeBookValidationService;
    private final GroupMemberValidationService groupMemberValidationService;

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;

    @Transactional
    public RecipeBookResponse create(Long memberId, RecipeBookCreateRequest request) {
        Member member = recipeBookValidationService.findMemberById(memberId);

        List<RecipeBook> existingBooks = recipeBookQueryService.findAllByMemberId(memberId);
        int nextSortOrder = existingBooks.stream()
                .mapToInt(RecipeBook::getSortOrder)
                .max()
                .orElse(0) + 1;

        RecipeBook recipeBook = RecipeBook.create(member, request.title(), false, nextSortOrder);
        RecipeBook saved = recipeBookRepository.save(recipeBook);

        return RecipeBookResponse.from(saved);
    }

    public List<RecipeBookResponse> findAllByMember(Long memberId) {
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByMemberId(memberId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public List<RecipeBookResponse> findAllByGroup(Long memberId, Long groupId) {
        Group group = recipeBookValidationService.findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, groupId);
        List<RecipeBook> recipeBooks = recipeBookQueryService.findAllByGroupId(groupId);
        return recipeBooks.stream()
                .map(RecipeBookResponse::from)
                .toList();
    }

    public RecipeBookResponse findById(Long memberId, Long id) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(id);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(id);
        List<RecipeSummaryResponse> recipes = items.stream()
                .map(item -> RecipeSummaryResponse.from(item.getRecipe()))
                .toList();

        return RecipeBookResponse.from(recipeBook, recipes);
    }

    @Transactional
    public void updateTitle(Long memberId, Long id, RecipeBookUpdateRequest request) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(id);
        recipeBookValidationService.validateNotGroupRecipeBook(recipeBook);
        recipeBookValidationService.validateRecipeBookOwnership(recipeBook, memberId);

        recipeBook.updateTitle(request.title());
    }

    @Transactional
    public void delete(Long memberId, Long id) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(id);
        recipeBookValidationService.validateNotGroupRecipeBook(recipeBook);
        recipeBookValidationService.validateRecipeBookOwnership(recipeBook, memberId);
        recipeBookValidationService.validateNotDefaultRecipeBook(recipeBook);

        List<RecipeBookItem> items = recipeBookItemRepository.findAllByRecipeBookIdWithRecipe(id);
        for (RecipeBookItem item : items) {
            item.getRecipe().decrementBookmarkCount();
        }

        recipeBookItemRepository.deleteAllByRecipeBookId(id);
        recipeBookRepository.delete(recipeBook);
    }

    @Transactional
    public void addRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);

        Recipe recipe = recipeBookValidationService.findRecipeById(recipeId);
        recipeBookValidationService.validateRecipeNotInBook(recipeBookId, recipeId);

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);

        recipe.incrementBookmarkCount();
    }

    @Transactional
    public void addRecipeIfNotExists(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        Recipe recipe = recipeBookValidationService.findRecipeById(recipeId);

        if (recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            return;
        }

        RecipeBookItem item = RecipeBookItem.create(recipeBook, recipe);
        recipeBookItemRepository.save(item);

        recipe.incrementBookmarkCount();
    }

    @Transactional
    public void removeRecipe(Long memberId, Long recipeBookId, Long recipeId) {
        RecipeBook recipeBook = recipeBookValidationService.findRecipeBookById(recipeBookId);
        recipeBookValidationService.validateRecipeBookAccess(recipeBook, memberId);
        recipeBookValidationService.validateRecipeInBook(recipeBookId, recipeId);

        long deleted = recipeBookItemRepository.deleteByRecipeBookIdAndRecipeId(recipeBookId,
                recipeId);
        if (deleted > 0) {
            Recipe recipe = recipeBookValidationService.findRecipeById(recipeId);
            recipe.decrementBookmarkCount();
        }
    }

    @Transactional
    public void createDefaultForMember(Member member) {
        if (recipeBookQueryService.findDefaultByMemberId(member.getId()).isPresent()) {
            return;
        }

        RecipeBook defaultBook = RecipeBook.create(member, "내 레시피북", true, 1);
        recipeBookRepository.save(defaultBook);
    }

    @Transactional
    public void createDefaultForMember(Long memberId) {
        Member member = recipeBookValidationService.findMemberById(memberId);
        createDefaultForMember(member);
    }

    @Transactional
    public void createDefaultForGroup(Long groupId, String groupName) {
        recipeBookValidationService.findGroupById(groupId);

        boolean hasDefault = recipeBookQueryService.findAllByGroupId(groupId).stream()
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
        List<RecipeBook> memberRecipeBooks = recipeBookQueryService.findAllByMemberId(memberId);

        recipeBookValidationService.validateReorderRequest(recipeBookIds, memberRecipeBooks);

        Map<Long, RecipeBook> recipeBookMap = memberRecipeBooks.stream()
                .collect(Collectors.toMap(RecipeBook::getId, book -> book));

        for (int i = 0; i < recipeBookIds.size(); i++) {
            RecipeBook book = recipeBookMap.get(recipeBookIds.get(i));
            book.updateSortOrder(i + 1);
        }

        recipeBookRepository.saveAll(recipeBookMap.values());
    }

    @Transactional
    public void moveRecipe(Long memberId, Long fromBookId, Long toBookId, Long recipeId) {
        if (fromBookId.equals(toBookId)) {
            return;
        }

        RecipeBook fromBook = recipeBookValidationService.findRecipeBookById(fromBookId);
        RecipeBook toBook = recipeBookValidationService.findRecipeBookById(toBookId);

        recipeBookValidationService.validateRecipeBookAccess(fromBook, memberId);
        recipeBookValidationService.validateRecipeBookAccess(toBook, memberId);

        recipeBookValidationService.validateRecipeNotInBook(toBookId, recipeId);

        recipeBookValidationService.validateRecipeInBook(fromBookId, recipeId);

        long updated = recipeBookItemRepository.moveRecipe(fromBookId, toBookId, recipeId);

        if (updated == 0) {
            throw new NotFoundException(ErrorCode.RECIPE_NOT_IN_BOOK);
        }
    }


}
