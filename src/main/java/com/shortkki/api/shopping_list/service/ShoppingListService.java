package com.shortkki.api.shopping_list.service;

import com.shortkki.api.group.entity.Group;
import com.shortkki.api.group.repository.GroupMemberRepository;
import com.shortkki.api.group.repository.GroupRepository;
import com.shortkki.api.ingredient.entity.Ingredient;
import com.shortkki.api.ingredient.repository.IngredientRepository;
import com.shortkki.api.recipe.entity.RecipeIngredient;
import com.shortkki.api.recipe.repository.RecipeIngredientRepository;
import com.shortkki.api.recipe.repository.RecipeRepository;
import com.shortkki.api.shopping_list.dto.request.ShoppingListItemRequest;
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
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;

    public List<ShoppingListResponse> getShoppingList(Long memberId, Long groupId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        return shoppingListRepository.findByGroupId(groupId).stream()
                .map(ShoppingListResponse::from)
                .toList();
    }

    @Transactional
    public ShoppingListResponse createShoppingList(Long memberId, Long groupId, String name, Long ingredientId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        Ingredient ingredient = findIngredient(ingredientId, name);
        ShoppingList shoppingList = ShoppingList.create(name, ingredient, group);
        return ShoppingListResponse.from(shoppingListRepository.save(shoppingList));
    }

    @Transactional
    public void createShoppingListBulk(Long memberId, Long groupId, List<ShoppingListItemRequest> items) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        List<ShoppingList> shoppingLists = items.stream()
                .map(item -> {
                    Ingredient ingredient = findIngredient(item.ingredientId(), item.name());
                    return ShoppingList.create(item.name(), ingredient, group);
                })
                .toList();
        shoppingListRepository.saveAll(shoppingLists);
    }

    @Transactional
    public void deleteShoppingList(Long memberId, Long groupId, Long shoppingListId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        ShoppingList shoppingList = findShoppingListById(shoppingListId);
        validateShoppingListInGroup(shoppingList, groupId);
        shoppingListRepository.delete(shoppingList);
    }

    @Transactional
    public void addRecipeIngredients(Long memberId, Long groupId, Long recipeId) {
        Group group = findGroupById(groupId);
        validateGroupMember(memberId, group);
        validateRecipeExists(recipeId);
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findByRecipeId(recipeId);
        List<ShoppingList> shoppingLists = recipeIngredients.stream()
                .map(ri -> ShoppingList.create(ri.getIngredient().getName(), ri.getIngredient(), group))
                .toList();
        shoppingListRepository.saveAll(shoppingLists);
    }

    private Ingredient findIngredient(Long ingredientId, String name) {
        if (ingredientId != null) {
            return ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.INGREDIENT_NOT_FOUND));
        }
        return ingredientRepository.findByName(name).orElse(null);
    }

    private void validateRecipeExists(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new NotFoundException(ErrorCode.RECIPE_NOT_FOUND);
        }
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
