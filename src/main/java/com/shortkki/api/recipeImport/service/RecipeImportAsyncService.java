package com.shortkki.api.recipeImport.service;

import com.shortkki.api.member.entity.Member;
import com.shortkki.api.member.repository.MemberRepository;
import com.shortkki.api.recipeBook.service.RecipeBookQueryService;
import com.shortkki.api.recipeBook.service.RecipeBookService;
import com.shortkki.api.recipeImport.dto.RecipeParseResult;
import com.shortkki.api.recipeImport.port.RecipeParserPort;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.ImportStatus;
import com.shortkki.api.source.domain.SourceImportHistory;
import com.shortkki.api.source.repository.SourceContentRepository;
import com.shortkki.api.source.repository.SourceImportHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeImportAsyncService {

    private final SourceContentRepository sourceContentRepository;
    private final MemberRepository memberRepository;
    private final RecipeBookService recipeBookService;
    private final RecipeBookQueryService recipeBookQueryService;
    private final SourceImportHistoryRepository sourceImportHistoryRepository;
    private final RecipeParserPort recipeParserPort;
    private final RecipeImportTransactionalService transactionalService;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Async
    public void processImport(
            Long memberId,
            Long sourceContentId,
            Long historyId,
            String sourceUrl) {
        SourceImportHistory history = sourceImportHistoryRepository.findById(historyId)
                .orElse(null);
        if (history == null) {
            log.warn("Source import history not found. historyId={}", historyId);
            return;
        }

        Member member = memberRepository.findById(memberId)
                .orElse(null);
        SourceContent sourceContent = sourceContentRepository.findById(sourceContentId)
                .orElse(null);

        if (member == null || sourceContent == null) {
            history.fail("Member or source content not found.");
            sourceImportHistoryRepository.save(history);
            return;
        }

        RecipeParseResult parseResult;
        String aiRawResponse = null;
        try {
            history.updateStatus(ImportStatus.PARSING);
            sourceImportHistoryRepository.save(history);
            log.info("AI 레시피 파싱 시작: {}", sourceUrl);
            parseResult = recipeParserPort.parseRecipeFromUrl(sourceUrl);
            aiRawResponse = parseResult.rawResponse();
            log.info("AI 파싱 완료 - 제목: {}, 재료: {}개, 순서: {}개",
                    parseResult.title(),
                    parseResult.ingredients().size(),
                    parseResult.steps().size());
        } catch (Exception e) {
            history.fail("AI parsing failed: " + e.getMessage(), aiRawResponse);
            sourceImportHistoryRepository.save(history);
            return;
        }

        // Store parsed result as JSON for user review and editing
        try {
            String parsedContentJson = objectMapper.writeValueAsString(parseResult);
            history.markAsParsed(aiRawResponse, parsedContentJson);
            sourceImportHistoryRepository.save(history);
            log.info("레시피 파싱 완료, 사용자 확인 대기 중. historyId={}", historyId);
            // TODO: 파싱 완료 알림 처리 - 사용자에게 검토 요청
        } catch (Exception e) {
            log.error("Failed to serialize parsed result", e);
            history.fail("Failed to store parsed result: " + e.getMessage());
            sourceImportHistoryRepository.save(history);
        }
    }

    private void addToDefaultRecipeBook(Long memberId, Long recipeId) {
        recipeBookQueryService.findDefaultByMemberId(memberId)
                .ifPresent(defaultBook -> recipeBookService.addRecipeIfNotExists(
                        memberId, defaultBook.getId(), recipeId));
    }
}
