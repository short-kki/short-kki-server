package com.shortkki.api.file.repository;

import com.shortkki.api.file.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long>, FileMetadataRepositoryCustom {

}
