package com.chookie.sense.user;

import com.chookie.sense.infrastructure.Service;

import static com.chookie.sense.twitter.TweetParser.getTwitterHandleFrom;

public class UserService implements Runnable {
    private final Service<TwitterUser> service;

    public UserService() {
        // TODO: create a new service that points to the twitter service,
        // and serves its own data on port 8083 and uri /users/
        final String twitterUrl = "ws://localhost:8081/tweets/";
        final String thisEnpointUrl = "/users/";
        final int thisPort = 8083;
        service = new Service<>(twitterUrl,
                thisEnpointUrl,
                thisPort, message -> new TwitterUser(getTwitterHandleFrom(message)));
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}