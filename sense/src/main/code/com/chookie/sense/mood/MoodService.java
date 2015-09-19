package com.chookie.sense.mood;

import com.chookie.sense.infrastructure.MessageHandler;
import com.chookie.sense.infrastructure.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class MoodService implements Runnable {
    private final Service<MoodyMessage> service;

    public MoodService() {
        // TODO: create a new service that connects to twitter,
        // and serves stuff at port 8082 and uri /moods/
        final String twitterUrl = "ws://localhost:8081/tweets/";
        final String thisEnpointUrl = "/moods/";
        final int thisPort = 8082;
        service = new Service<>(twitterUrl,
                thisEnpointUrl, thisPort,
                MoodAnalyser::analyseMood);
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) throws IOException, DeploymentException {
        new MoodService().run();
    }
}