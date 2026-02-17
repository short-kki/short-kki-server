package com.shortkki.api.member.service;

import com.shortkki.api.file.application.service.FileMetadataQueryService;
import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.api.member.entity.Member;
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
        FileMetadata profileImgFile = fileMetadataQueryService.findById(profileImgFileId);
        member.updateName(name);
        member.updateProfileImgFile(profileImgFile);
    }

    public void withdraw(Long memberId) {
        Member member = memberQueryService.findMember(memberId);
        member.delete();
    }
}
