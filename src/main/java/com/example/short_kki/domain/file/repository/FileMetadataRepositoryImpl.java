package com.example.short_kki.domain.file.repository;

import com.example.short_kki.domain.file.entity.QFileMetadata;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileMetadataRepositoryImpl implements FileMetadataRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QFileMetadata fileMetadata = QFileMetadata.fileMetadata;

}
