package com.shortkki.api.shopping_list.service;

import com.shortkki.api.group.application.service.GroupMemberValidationService;
import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.shopping_list.dto.request.ShoppingListItemRequest;
import com.shortkki.api.shopping_list.dto.response.ShoppingListResponse;
import com.shortkki.api.shopping_list.entity.ShoppingList;
import com.shortkki.api.shopping_list.repository.ShoppingListRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingListService {

    private final GroupMemberValidationService groupMemberValidationService;

    private final ShoppingListRepository shoppingListRepository;
    private final GroupRepository groupRepository;

    public List<ShoppingListResponse> getShoppingList(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        return shoppingListRepository.findByGroupId(groupId).stream()
                .map(ShoppingListResponse::from)
                .toList();
    }

    @Transactional
    public void createShoppingListBulk(Long memberId, Long groupId,
            List<ShoppingListItemRequest> items) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
        Set<String> existingNames = shoppingListRepository.findNamesByGroupId(groupId);
        List<ShoppingList> shoppingLists = items.stream()
                .map(ShoppingListItemRequest::name)
                .filter(name -> !existingNames.contains(name))
                .map(name -> ShoppingList.create(name, null, group))
                .toList();
        shoppingListRepository.saveAll(shoppingLists);
    }

    @Transactional
    public void deleteShoppingList(Long memberId, Long groupId, Long shoppingListId) {
        Group group = findGroupById(groupId);
        groupMemberValidationService.validateGroupMember(memberId, group.getId());
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

    private void validateShoppingListInGroup(ShoppingList shoppingList, Long groupId) {
        if (!shoppingList.getGroup().getId().equals(groupId)) {
            throw new BadRequestException(ErrorCode.SHOPPING_LIST_NOT_IN_GROUP);
        }
    }
}
