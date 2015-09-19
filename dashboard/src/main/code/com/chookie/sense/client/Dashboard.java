package com.chookie.sense.client;

import com.chookie.sense.client.mood.HappinessChartData;
import com.chookie.sense.client.mood.MoodChartData;
import com.chookie.sense.client.mood.MoodsParser;
import com.chookie.sense.client.mood.TweetMood;
import com.chookie.sense.client.user.LeaderboardData;
import com.chookie.sense.infrastructure.ClientEndpoint;
import com.chookie.sense.infrastructure.MessageHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemResource;

public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // all models created in advance
        LeaderboardData leaderboardData = new LeaderboardData();
        MoodChartData moodChartData = new MoodChartData();
        HappinessChartData happinessChartData = new HappinessChartData();

        // ToDo: wire up the models to the services they're getting the data from
        ClientEndpoint<String> userEndpoint = ClientEndpoint.createPassthroughEndpoint("ws://localhost:8083/users/");
        userEndpoint.addListener(leaderboardData);
        userEndpoint.connect();

        ClientEndpoint<TweetMood> moodEndpoint = new ClientEndpoint<TweetMood>("ws://localhost:8082/moods/", MoodsParser::parse);
        moodEndpoint.addListener(moodChartData);
        moodEndpoint.addListener(happinessChartData);
        moodEndpoint.connect();

        // initialise the UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        primaryStage.setTitle("Twitter Dashboard");
        Scene scene = new Scene(loader.load(), 1024, 1024);
        scene.getStylesheets().add("dashboard.css");

        // wire up the models to the controllers
        DashboardController dashboardController = loader.getController();
        dashboardController.getLeaderboardController().setData(leaderboardData);
        dashboardController.getMoodController().setData(moodChartData);
        dashboardController.getHappinessController().setData(happinessChartData);

        // let's go!
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
