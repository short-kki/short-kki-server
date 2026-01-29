package com.shortkki.global.response.page;

import org.springframework.data.domain.Slice;

public record SlicePageInfoResponse(
        int page,
        int size,
        boolean hasNext,
        boolean first,
        boolean last
) {
    public static SlicePageInfoResponse from(Slice<?> slice) {
        return new SlicePageInfoResponse(
                slice.getNumber(),
                slice.getSize(),
                slice.hasNext(),
                slice.isFirst(),
                slice.isLast()
        );
    }
}
