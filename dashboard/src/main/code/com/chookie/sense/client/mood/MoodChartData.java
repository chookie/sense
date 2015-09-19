package com.chookie.sense.client.mood;
import com.chookie.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import static javafx.collections.FXCollections.observableArrayList;

public class MoodChartData implements MessageListener<TweetMood> {
    private final PieChart.Data sadPortion = new PieChart.Data("Sad", 0);
    private final PieChart.Data happyPortion = new PieChart.Data("Happy", 0);
    private final PieChart.Data confusedPortion = new PieChart.Data("Errr...", 0);
    private final ObservableList<PieChart.Data> pieChartData = observableArrayList(sadPortion, happyPortion, confusedPortion);

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    @Override
    public void onMessage(TweetMood message) {
        incrementPie(message.isHappy(), new Runnable() {
            public void run() {
                happyPortion.setPieValue(happyPortion.getPieValue() + 1);
            }
        });
        incrementPie(message.isSad(), new Runnable() {
            public void run() {
                sadPortion.setPieValue(happyPortion.getPieValue() + 1);
            }
        });
        incrementPie(message.isConfused(), new Runnable() {
            public void run() {
                confusedPortion.setPieValue(happyPortion.getPieValue() + 1);
            }
        });
    }

    private void incrementPie(boolean happy, Runnable runnable) {
        if(happy) {
            runnable.run();
        }
    }
}
