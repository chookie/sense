package com.chookie.sense.client;

import com.chookie.sense.infrastructure.BroadcastingServerEndpoint;

import java.util.function.Supplier;

/**
 * Created by Alison on 19/09/15.
 */
public class StubService {
    private final BroadcastingServerEndpoint<String> serverEndpoint = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server;

    public StubService(String path, int port, Supplier<String> messageGenerator) {

    }
}
