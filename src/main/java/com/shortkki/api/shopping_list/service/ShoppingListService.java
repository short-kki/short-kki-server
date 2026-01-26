package com.shortkki.api.shopping_list.service;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.shopping_list.dto.response.ShoppingListResponse;
import com.shortkki.api.shopping_list.entity.ShoppingList;
import com.shortkki.api.shopping_list.repository.ShoppingListRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public List<ShoppingListResponse> getShoppingList(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        return shoppingListRepository.findByGroupId(groupId).stream()
                .map(ShoppingListResponse::from)
                .toList();
    }

    @Transactional
    public ShoppingListResponse createShoppingList(Long memberId, Long groupId, String name) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        ShoppingList shoppingList = ShoppingList.create(name, null, group);
        return ShoppingListResponse.from(shoppingListRepository.save(shoppingList));
    }

    @Transactional
    public void updateShoppingList(Long memberId, Long groupId, Long shoppingListId, String name) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        ShoppingList shoppingList = findShoppingListById(shoppingListId);
        validateShoppingListInGroup(shoppingList, groupId);
        shoppingList.updateName(name);
    }

    @Transactional
    public void deleteShoppingList(Long memberId, Long groupId, Long shoppingListId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        ShoppingList shoppingList = findShoppingListById(shoppingListId);
        validateShoppingListInGroup(shoppingList, groupId);
        shoppingListRepository.delete(shoppingList);
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.GROUP_NOT_FOUND));
    }

    private ShoppingList findShoppingListById(Long shoppingListId) {
        return shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SHOPPING_LIST_NOT_FOUND));
    }

    private void validateGroupMember(Long memberId, Group group) {
        if (!groupMemberRepository.existsByMemberIdAndGroup(memberId, group)) {
            throw new AccessDeniedException(ErrorCode.GROUP_NOT_MEMBER);
        }
    }

    private void validateShoppingListInGroup(ShoppingList shoppingList, Long groupId) {
        if (!shoppingList.getGroup().getId().equals(groupId)) {
            throw new BadRequestException(ErrorCode.SHOPPING_LIST_NOT_IN_GROUP);
        }
    }
}
