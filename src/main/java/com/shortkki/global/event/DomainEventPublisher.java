package com.shortkki.global.event;

public interface DomainEventPublisher {
    void publish(Object event);
}
