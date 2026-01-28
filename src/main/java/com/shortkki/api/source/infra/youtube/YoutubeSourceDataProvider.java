package com.shortkki.api.source.infra.youtube;

import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.dto.SourceContentInfo;
import com.shortkki.api.source.service.port.SourceDataProvider;
import com.shortkki.global.error.exception.NotImplementedException;
import org.springframework.stereotype.Component;

@Component
public class YoutubeSourceDataProvider implements SourceDataProvider {

    @Override
    public SourceContentInfo getSourceInfo(String url) {
        throw new NotImplementedException();
    }

    @Override
    public SourceCreatorInfo getSourceCreatorInfo(String externalKey) {
        throw new NotImplementedException();
    }
}
