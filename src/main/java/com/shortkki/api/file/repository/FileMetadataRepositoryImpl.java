package com.shortkki.api.file.repository;

import com.shortkki.api.file.entity.QFileMetadata;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileMetadataRepositoryImpl implements FileMetadataRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QFileMetadata fileMetadata = QFileMetadata.fileMetadata;

}
