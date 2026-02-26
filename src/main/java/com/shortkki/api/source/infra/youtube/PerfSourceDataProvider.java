package com.shortkki.api.source.infra.youtube;

import com.shortkki.api.source.service.dto.SourceContentInfo;
import com.shortkki.api.source.service.dto.SourceCreatorInfo;
import com.shortkki.api.source.service.port.SourceDataProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("perf")
public class PerfSourceDataProvider implements SourceDataProvider {

    @Override
    public SourceContentInfo getSourceInfo(String externalKey) {
        return new SourceContentInfo(
                externalKey,
                "perf-source-title-" + externalKey,
                "https://www.youtube.com/watch?v=" + externalKey,
                null,
                "perf-channel",
                true,
                120
        );
    }

    @Override
    public SourceCreatorInfo getSourceCreatorInfo(String externalKey) {
        return new SourceCreatorInfo(
                externalKey != null && !externalKey.isBlank() ? externalKey : "perf-channel",
                "Perf Creator",
                null
        );
    }
}
