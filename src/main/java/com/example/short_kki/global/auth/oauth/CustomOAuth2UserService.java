package com.example.short_kki.global.auth.oauth;

import com.example.short_kki.domain.member.entity.Member;
import com.example.short_kki.domain.member.repository.MemberRepository;
import com.example.short_kki.global.auth.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    OAuth2UserInfo userInfo = OAuth2UserInfo.of(registrationId, attributes);
    log.info("OAuth2 Login attempt - provider: {}, email: {}", userInfo.getProvider(),
        userInfo.getEmail());

    Member member = getOrCreateMember(userInfo);

    return LoginMember.of(member, attributes);
  }

  private Member getOrCreateMember(OAuth2UserInfo userInfo) {
    return memberRepository.findByEmail(userInfo.getEmail())
        .map(existingMember -> updateMemberIfNeeded(existingMember, userInfo))
        .orElseGet(() -> createNewMember(userInfo));
  }

  private Member updateMemberIfNeeded(Member member, OAuth2UserInfo userInfo) {
    boolean needsUpdate = false;

    if (!member.getName().equals(userInfo.getName())) {
      member.updateName(userInfo.getName());
      needsUpdate = true;
    }

    if (member.getOauthId() == null || !member.getOauthId().equals(userInfo.getOauthId())) {
      member.updateOAuthInfo(userInfo.getOauthId(), userInfo.getProvider());
      needsUpdate = true;
    }

    if (needsUpdate) {
      log.info("Updated existing member: {}", member.getEmail());
    }

    return member;
  }

  private Member createNewMember(OAuth2UserInfo userInfo) {
    Member newMember = Member.create(
        userInfo.getEmail(),
        userInfo.getName(),
        userInfo.getOauthId(),
        userInfo.getProvider()
    );

    Member savedMember = memberRepository.save(newMember);
    log.info("Created new member via OAuth2: {}", savedMember.getEmail());

    return savedMember;
  }
}
