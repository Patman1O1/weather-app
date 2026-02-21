package edu.cs342.project2;

import javafx.application.Application;
import javafx.stage.Stage;

public final class WeatherApplication extends Application {
    /* ---------------------------------------------------Fields----------------------------------------------------- */

    /* ---------------------------------------------------Methods---------------------------------------------------- */
    public  static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        try {


            primaryStage.setTitle("Weather");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}