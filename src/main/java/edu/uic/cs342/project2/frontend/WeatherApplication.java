package edu.cs342.project2.frontend;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WeatherApplication extends Application {
    // ── Fields ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private SceneManager sceneManager;

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load fonts
            Font.loadFont(getClass().getResourceAsStream("/fonts/open-sans/OpenSans-Regular.ttf"), 14);

            this.sceneManager = SceneManager.getInstance();
            this.sceneManager.setStage(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}