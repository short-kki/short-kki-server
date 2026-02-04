package com.shortkki.api.curation.service;

import com.shortkki.api.curation.controller.dto.request.CreateCurationRequest;
import com.shortkki.api.curation.controller.dto.response.CurationResponse;
import com.shortkki.api.curation.entity.Curation;
import com.shortkki.api.curation.repository.CurationRepository;
import com.shortkki.global.error.ErrorCode;
import com.shortkki.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurationService {

    private final CurationRepository curationRepository;

    public CurationResponse create(CreateCurationRequest request) {
        validateTitleNotDuplicate(request.title());

        Curation curation = Curation.create(
                request.title(),
                request.description(),
                request.dayTypes(),
                request.timeTypes(),
                request.cuisineTypes(),
                request.mealTypes(),
                request.difficulties(),
                request.keywords(),
                request.tags(),
                request.ingredients()
        );
        curationRepository.save(curation);
        return CurationResponse.from(curation);
    }

    private void validateTitleNotDuplicate(String title) {
        if (curationRepository.existsByTitle(title)) {
            throw new BusinessException(ErrorCode.CURATION_TITLE_DUPLICATE);
        }
    }
}
