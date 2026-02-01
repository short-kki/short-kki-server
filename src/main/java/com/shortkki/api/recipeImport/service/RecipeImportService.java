package com.shortkki.api.recipeImport.service;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipeImport.dto.RecipeImportRequest;
import com.shortkki.api.recipeImport.dto.RecipeImportPreview;
import com.shortkki.api.recipeImport.dto.RecipeImportPayload;
import com.shortkki.api.recipeImport.dto.RecipeImportStatusPayload;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.domain.SourcePlatform;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.api.source.repository.SourceImportHistoryRepository;
import com.shortkki.api.source.service.SourceContentService;
import com.shortkki.api.source.support.ExternalKeyExtractorRegistry;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.AccessDeniedException;
import com.shortkki.global.error.exception.BadRequestException;
import com.shortkki.global.error.exception.BusinessException;
import com.shortkki.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeImportService {

    private final SourceContentService sourceContentService;
    private final SourceContentRepository sourceContentRepository;
    private final ExternalKeyExtractorRegistry extractorRegistry;
    private final MemberRepository memberRepository;
    private final SourceImportHistoryRepository sourceImportHistoryRepository;
    private final RecipeImportAsyncService recipeImportAsyncService;

    public RecipeImportPayload importFromUrl(Long memberId, RecipeImportRequest request) {
        Member member = findMemberById(memberId);
        String sourceUrl = request.sourceUrl();

        validateNotDuplicateSource(sourceUrl);

        SourceContent sourceContent = sourceContentService.resolveSourceContent(sourceUrl);

        SourceImportHistory history = SourceImportHistory.create(
                member.getId(),
                sourceContent.getId(),
                sourceUrl,
                sourceContent.getPlatform());
        sourceImportHistoryRepository.save(history);

        SourceContent previewContent = sourceContentRepository.findByIdWithCreator(sourceContent.getId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
        RecipeImportPreview preview = RecipeImportPreview.from(previewContent);

        recipeImportAsyncService.processImport(
                member.getId(),
                sourceContent.getId(),
                history.getId(),
                sourceUrl);

        return RecipeImportPayload.accepted(history.getId(), sourceUrl, preview);
    }

    public RecipeImportStatusPayload getStatus(Long memberId, Long historyId) {
        SourceImportHistory history = sourceImportHistoryRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));

        if (history.getMemberId() == null || !history.getMemberId().equals(memberId)) {
            throw new AccessDeniedException(ErrorCode.ACCESS_DENIED);
        }

        SourceContent previewContent = sourceContentRepository.findByIdWithCreator(history.getSourceContentId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ERROR));
        RecipeImportPreview preview = RecipeImportPreview.from(previewContent);

        return RecipeImportStatusPayload.from(history, preview);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateNotDuplicateSource(String sourceUrl) {
        SourcePlatform platform = extractorRegistry.detectPlatform(sourceUrl)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNSUPPORTED_SOURCE_PLATFORM));
        String externalKey = extractorRegistry.extractKey(platform, sourceUrl);

        if (sourceContentRepository.findByPlatformAndExternalKey(platform, externalKey).isPresent()) {
            throw new BusinessException(ErrorCode.SOURCE_CONTENT_ALREADY_EXISTS);
        }
    }
}
