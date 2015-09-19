package com.chookie.sense.client.user;

import com.chookie.sense.infrastructure.MessageListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

    @Override
    public void onMessage(String message) {
        TwitterUser twitterUser = allTwitterUsers.computeIfAbsent(message, TwitterUser::new);

        twitterUser.incrementCount();

        List<TwitterUser> topTweeters = allTwitterUsers.values().stream()
                .sorted(comparing(TwitterUser::getTweets).reversed())
                .limit(NUMBER_OF_LEADERS)
                .collect(Collectors.toList());

        Platform.runLater( () -> items.setAll(topTweeters));
    }
}
