package com.shortkki.api.member.service;

import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.member.entity.Member;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BadRequestException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberQueryService memberQueryService;
    private final FileMetadataQueryService fileMetadataQueryService;

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
        if (requestImageId == null && currentImageId != null) {
            member.getProfileImgFile().markDeleted();
            member.removeProfileImgFile();
            return;
        }
        if (requestImageId != null) {
            if (member.getProfileImgFile() != null) {
                member.getProfileImgFile().markDeleted();
            }
            FileMetadata newImage = fileMetadataQueryService.findByIdWithOwnerValidation(requestImageId, memberId);
            member.updateProfileImgFile(newImage);
        }
    }

    public void withdraw(Long memberId) {
        Member member = memberQueryService.findMember(memberId);
        validateNotDeleted(member);
        member.delete();
    }

    private void validateNotDeleted(Member member) {
        if (member.isDeleted()) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_DELETED);
        }
    }
}
