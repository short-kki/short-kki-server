package com.shortkki.api.member.service;

import com.shortkki.api.auth.application.port.AccessTokenBlacklist;
import com.shortkki.api.auth.application.port.RefreshTokenStore;
import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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

    public void withdraw(Long memberId, String jti, Instant exp) {
        Member member = memberQueryService.findMember(memberId);
        validateNotDeleted(member);
        member.delete();
        Duration remainingValidity = Duration.between(Instant.now(), exp);
        if (remainingValidity.isNegative()) {
            remainingValidity = Duration.ZERO;
        }
        final Duration ttl = remainingValidity;
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                refreshTokenStore.delete(memberId);
                accessTokenBlacklist.add(jti, ttl);
            }
        });
    }

    private void validateNotDeleted(Member member) {
        if (member.isDeleted()) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_DELETED);
        }
    }
}
