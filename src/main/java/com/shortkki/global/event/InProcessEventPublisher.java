package com.shortkki.global.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InProcessEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}
