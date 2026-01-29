package com.shortkki.api.application.usecase;

import com.shortkki.api.group.dto.request.CreateGroupRequest;
import com.shortkki.api.group.dto.response.GroupResponse;
import com.shortkki.api.group.service.GroupService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateGroupUseCase {

    private final GroupService groupService;
    private final RecipeBookService recipeBookService;

    @Transactional
    public GroupResponse execute(Long memberId, CreateGroupRequest request) {
        GroupResponse response = groupService.createGroup(memberId, request);
        recipeBookService.createDefaultForGroup(response.id(), response.name());
        return response;
    }
}
