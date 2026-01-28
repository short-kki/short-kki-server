package com.shortkki.api.source.service.port;

import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.dto.SourceContentInfo;

public interface SourceDataProvider {

    SourceContentInfo getSourceInfo(String externalKey);
    SourceCreatorInfo getSourceCreatorInfo(String externalKey);
}
