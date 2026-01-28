package com.shortkki.api.source.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.source.domain.QSourceContent;
import com.shortkki.api.source.domain.SourceContent;
import com.shortkki.api.source.domain.SourcePlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SourceContentRepositoryImpl implements SourceContentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QSourceContent sourceContent = QSourceContent.sourceContent;

    @Override
    public Optional<SourceContent> findByPlatformAndExternalKey(SourcePlatform platform, String externalKey) {
        SourceContent result = queryFactory
                .selectFrom(sourceContent)
                .where(
                        sourceContent.platform.eq(platform),
                        sourceContent.externalKey.eq(externalKey)
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
