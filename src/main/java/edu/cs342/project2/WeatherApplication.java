package edu.cs342.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class WeatherApplication extends Application {
    /* ---------------------------------------------------Fields----------------------------------------------------- */

    /* ---------------------------------------------------Methods---------------------------------------------------- */
    public  static void main(String[] args) { launch(args); }

    public void loadButton(Stage primaryStage) {
        Button button = new Button("Click Me");

        button.setOnAction(e -> {
            System.out.println("Button was clicked!");
        });

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Weather App");
            primaryStage.setScene(new GoogleMapScene());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}