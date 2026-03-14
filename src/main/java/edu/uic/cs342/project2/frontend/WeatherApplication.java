package edu.uic.cs342.project2.frontend;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WeatherApplication extends Application {
    // ── Fields ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private final StageManager stageManager;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private WeatherApplication() { this.stageManager = StageManager.getInstance(); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) { Application.launch(args); }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.stageManager.load(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}