package com.shortkki.api.notification.repository;

import static com.shortkki.api.notification.entity.QNotification.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shortkki.api.notification.entity.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Notification> findByReceiverIdWithCursor(Long receiverId, Long cursorId, Pageable pageable) {
        List<Notification> results = queryFactory
                .selectFrom(notification)
                .where(
                        notification.receiverId.eq(receiverId),
                        cursorIdCondition(cursorId)
                )
                .orderBy(notification.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results = results.subList(0, pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression cursorIdCondition(Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return notification.id.lt(cursorId);
    }
}
