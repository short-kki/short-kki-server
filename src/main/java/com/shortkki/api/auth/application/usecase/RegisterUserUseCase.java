package com.shortkki.api.auth.application.usecase;

import com.shortkki.api.auth.dto.LoginRequest;
import com.shortkki.api.auth.dto.LoginResponse;
import com.shortkki.api.auth.application.service.AuthService;
import com.shortkki.api.member.entity.OAuthProvider;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final AuthService authService;
    private final RecipeBookService recipeBookService;

    @Transactional
    public LoginResponse execute(OAuthProvider provider, LoginRequest request) {
        LoginResponse response = authService.login(provider, request);

        if (response.isNewMember()) {
            recipeBookService.createDefaultForMember(response.getMemberId());
        }

        return response;
    }
}
