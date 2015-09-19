package com.chookie.sense.client;

import com.chookie.sense.infrastructure.BroadcastingServerEndpoint;
import com.chookie.sense.infrastructure.DaemonThreadFactory;
import com.chookie.sense.infrastructure.WebSocketServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class StubService implements Runnable {
    private final BroadcastingServerEndpoint<String> serverEndpoint = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server;
    private final Supplier<String> messageGenerator;

    public StubService(String path, int port, Supplier<String> messageGenerator) {
        this.server = new WebSocketServer(path, port, serverEndpoint);
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void run() {
        // start the websocket server endpoint on a daemon thread
        newSingleThreadExecutor(new DaemonThreadFactory()).submit(server);

        // start the message generator on another thread at an interval
        ScheduledFuture<?> scheduledFuture = newScheduledThreadPool(1).scheduleAtFixedRate(
                () -> serverEndpoint.onMessage(messageGenerator.get()),
                0,500, TimeUnit.MILLISECONDS);

        try {
            scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
