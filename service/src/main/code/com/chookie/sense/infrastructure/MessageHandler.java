package com.chookie.sense.infrastructure;

@FunctionalInterface
public interface MessageHandler<T> {
    T processMessage(String message);
}
