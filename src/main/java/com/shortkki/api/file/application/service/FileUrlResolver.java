package com.shortkki.api.file.application.service;

import com.shortkki.api.file.entity.FileMetadata;
import java.util.List;

public interface FileUrlResolver {

    String getUrl(FileMetadata file);

    List<String> getUrls(List<FileMetadata> files);
}
