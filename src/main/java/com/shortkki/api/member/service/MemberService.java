package com.shortkki.api.member.service;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import com.shortkki.api.auth.application.port.RefreshTokenStore;
import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberQueryService memberQueryService;
    private final FileMetadataQueryService fileMetadataQueryService;
    private final RefreshTokenStore refreshTokenStore;
    private final AccessTokenBlacklist accessTokenBlacklist;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidityMs;

    public void updateProfile(Long memberId, String name, Long profileImgFileId) {
        Member member = memberQueryService.findMember(memberId);
        validateNotDeleted(member);
        member.updateName(name);
        updateProfileImage(member, profileImgFileId, memberId);
    }

    private void updateProfileImage(Member member, Long requestImageId, Long memberId) {
        Long currentImageId = member.getProfileImgFile() != null
                ? member.getProfileImgFile().getId()
                : null;
        if (Objects.equals(currentImageId, requestImageId)) {
            return;
        }
        if (member.getProfileImgFile() != null) {
            member.getProfileImgFile().markDeleted();
            member.removeProfileImgFile();
        }
        if (requestImageId != null) {
            FileMetadata newImage = fileMetadataQueryService.findByIdWithOwnerValidation(requestImageId, memberId);
            member.updateProfileImgFile(newImage);
        }
    }

    public void withdraw(Long memberId, String jti) {
        Member member = memberQueryService.findMember(memberId);
        validateNotDeleted(member);
        member.delete();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                refreshTokenStore.delete(memberId);
                accessTokenBlacklist.add(jti, Duration.ofMillis(accessTokenValidityMs));
            }
        });
    }

    private void validateNotDeleted(Member member) {
        if (member.isDeleted()) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_DELETED);
        }
    }
}
