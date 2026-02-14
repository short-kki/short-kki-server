package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeBookValidationService {

    private final GroupMemberValidationService groupMemberValidationService;
    private final RecipeBookQueryService recipeBookQueryService;

    private final RecipeBookItemRepository recipeBookItemRepository;

    public void validateReorderable(List<Long> requestIds, List<RecipeBook> memberBooks) {
        if (requestIds.size() != memberBooks.size()) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }

        Set<Long> requestIdSet = new HashSet<>(requestIds);
        if (requestIdSet.size() != requestIds.size()) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }

        Set<Long> memberBookIds = memberBooks.stream()
                .map(RecipeBook::getId)
                .collect(Collectors.toSet());

        if (!requestIdSet.equals(memberBookIds)) {
            throw new BadRequestException(ErrorCode.INVALID_REORDER_REQUEST);
        }
    }

    public void validateRecipeBookOwnership(RecipeBook recipeBook, Long memberId) {
        if (!recipeBook.isOwnedByMember(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void validateNotDefaultRecipeBook(RecipeBook recipeBook) {
        if (recipeBook.getIsDefault()) {
            throw new BadRequestException(ErrorCode.CANNOT_DELETE_DEFAULT_RECIPE_BOOK);
        }
    }

    public void validateRecipeNotInBook(Long recipeBookId, Long recipeId) {
        if (recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new BadRequestException(ErrorCode.RECIPE_ALREADY_IN_BOOK);
        }
    }

    public void validateRecipeInBook(Long recipeBookId, Long recipeId) {
        if (!recipeBookItemRepository.existsByRecipeBookIdAndRecipeId(recipeBookId, recipeId)) {
            throw new NotFoundException(ErrorCode.RECIPE_NOT_IN_BOOK);
        }
    }

    public void validateNotGroupRecipeBook(RecipeBook recipeBook) {
        if (recipeBook.getGroupId() != null) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void validateRecipeBookAccess(RecipeBook recipeBook, Long memberId) {
        if (recipeBook.getGroupId() != null) {
            recipeBookQueryService.findGroupById(recipeBook.getGroupId());
            groupMemberValidationService.validateGroupMember(memberId, recipeBook.getGroupId());
        } else {
            validateRecipeBookOwnership(recipeBook, memberId);
        }
    }

    public void validateDeletable(Long memberId, Long recipeBookId) {
        RecipeBook recipeBook = recipeBookQueryService.findRecipeBookById(recipeBookId);
        validateNotGroupRecipeBook(recipeBook);
        validateRecipeBookOwnership(recipeBook, memberId);
        validateNotDefaultRecipeBook(recipeBook);
    }
}
