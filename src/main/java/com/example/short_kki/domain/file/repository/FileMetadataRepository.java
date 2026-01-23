package com.example.short_kki.domain.file.repository;

import com.example.short_kki.domain.file.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long>, FileMetadataRepositoryCustom {

}
