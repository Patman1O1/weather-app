package edu.uic.cs342.project2.frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static volatile SceneManager instance;

    private Scene dashboardScene, forecastScene, mapScene, settingsScene;

    private Stage stage;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private SceneManager() {
        this.stage = null;
        this.dashboardScene = null;
        this.forecastScene = null;
        this.mapScene = null;
        this.settingsScene = null;
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setStage(Stage stage) throws NullPointerException {
        if (stage == null) {
            throw new NullPointerException("stage is null");
        }
        this.stage = stage;
        this.stage.show();
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static synchronized SceneManager getInstance() {
        if (SceneManager.instance == null) {
            SceneManager.instance = new SceneManager();
        }
        return SceneManager.instance;
    }
}
