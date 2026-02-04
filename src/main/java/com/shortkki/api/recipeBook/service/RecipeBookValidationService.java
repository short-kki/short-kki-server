package com.shortkki.api.recipeBook.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipe.entity.Recipe;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.recipeBook.entity.RecipeBook;
import com.shortkki.api.recipeBook.repository.RecipeBookItemRepository;
import com.shortkki.api.recipeBook.repository.RecipeBookRepository;
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

    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookItemRepository recipeBookItemRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public void validateReorderRequest(List<Long> requestIds, List<RecipeBook> memberBooks) {
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
            Group group = findGroupById(recipeBook.getGroupId());
            groupMemberValidationService.validateGroupMember(memberId, group.getId());
        } else {
            validateRecipeBookOwnership(recipeBook, memberId);
        }
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public RecipeBook findRecipeBookById(Long recipeBookId) {
        return recipeBookRepository.findById(recipeBookId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_BOOK_NOT_FOUND));
    }

    public Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RECIPE_NOT_FOUND));
    }

    public Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }
}
