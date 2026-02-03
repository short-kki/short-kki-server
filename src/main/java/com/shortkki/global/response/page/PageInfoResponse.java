package com.shortkki.global.response.page;

import org.springframework.data.domain.Page;

public record PageInfoResponse(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
    public static PageInfoResponse from(Page<?> page) {
        return new PageInfoResponse(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}