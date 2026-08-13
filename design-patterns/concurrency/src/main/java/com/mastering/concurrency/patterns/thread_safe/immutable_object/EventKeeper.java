package com.mastering.concurrency.patterns.thread_safe.immutable_object;

import lombok.Getter;

import java.util.UUID;

/**
 * Pattern: Immutable Object with Volatile Reference
 * <p>
 * Example: Event keeper
 */
@Getter
public class EventKeeper {

    private volatile Event lastEvent = new Event(null, null, null, null, null);

    public void acceptEvent(String name, String type, String username, byte[] payload) {
        if (type.equals("STORAGE")) {
            lastEvent = new Event(UUID.randomUUID().toString(), name, type, username, payload);
        }
    }

    public record Event(String id, String name, String type, String username, byte[] payload) {
    }

}
