package com.chookie.sense.infrastructure;

@FunctionalInterface
public interface MessageListener<T> {
    void onMessage(T message);
}
