package edu.uic.cs342.project2.frontend;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public final class StageManager {
    // ── Scenes ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public static enum SceneType {
        // ── Constants ────────────────────────────────────────────────────────────────────────────────────────────────
        CURRENT_WEATHER,
        HOURLY_FORECAST,
        SEVEN_DAY_FORECAST,
        LOCATIONS,
        MAP,
        SETTINGS,
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final int SCENE_COUNT = 6;

    private static final double FONT_SIZE = 16.0;

    private static final String REGULAR_FONT_PATH = "/styles/fonts/open-sans/OpenSans-Regular.ttf";

    private static final String BOLD_FONT_PATH = "/styles/fonts/open-sans/OpenSans-Bold.ttf";

    private static final String ITALIC_FONT_PATH = "/styles/fonts/open-sans/OpenSans-Italic.ttf";

    private static final String STYLESHEET_PATH = "/styles/css/index.css";

    private static final String CURRENT_WEATHER_FXML_PATH = "/fxml/current_weather.fxml";

    private static final String HOURLY_FORECAST_FXML_PATH = "/fxml/hourly_forecast.fxml";

    private static final String SEVEN_DAY_FORECAST_FXML_PATH = "/fxml/seven_day_forecast.fxml";

    private static final String LOCATIONS_FXML_PATH = "/fxml/locations.fxml";

    private static final String MAP_FXML_PATH = "/fxml/map.fxml";

    private static final String SETTINGS_FXML_PATH = "/fxml/settings.fxml";

    private static volatile StageManager instance;

    private Stage stage;

    private Map<SceneType, Scene> scenes;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private StageManager() { this.stage = null; this.scenes = null; }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static StageManager getInstance() { return StageManager.instance; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private Scene loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(this.getClass().getResource(StageManager.STYLESHEET_PATH)).toExternalForm()
        );
        return scene;
    }

    public void load(Stage stage) throws IOException, NullPointerException {
        // Set the stage
        if (stage == null) {
            throw new NullPointerException("stage is null");
        }
        this.stage = stage;

        // Load fonts
        Font.loadFont(getClass().getResourceAsStream(StageManager.REGULAR_FONT_PATH), StageManager.FONT_SIZE);
        Font.loadFont(getClass().getResourceAsStream(StageManager.BOLD_FONT_PATH), StageManager.FONT_SIZE);
        Font.loadFont(getClass().getResourceAsStream(StageManager.ITALIC_FONT_PATH), StageManager.FONT_SIZE);

        // Load scenes
        this.scenes = new HashMap<>(StageManager.SCENE_COUNT);
        this.scenes.put(SceneType.CURRENT_WEATHER, this.loadScene(StageManager.CURRENT_WEATHER_FXML_PATH));
        this.scenes.put(SceneType.HOURLY_FORECAST, this.loadScene(StageManager.HOURLY_FORECAST_FXML_PATH));
        this.scenes.put(SceneType.SEVEN_DAY_FORECAST, this.loadScene(StageManager.SEVEN_DAY_FORECAST_FXML_PATH));
        this.scenes.put(SceneType.LOCATIONS, this.loadScene(StageManager.LOCATIONS_FXML_PATH));
        this.scenes.put(SceneType.MAP, this.loadScene(StageManager.MAP_FXML_PATH));
        this.scenes.put(SceneType.SETTINGS, this.loadScene(StageManager.SETTINGS_FXML_PATH));

        // Show the stage
        this.stage.show();
    }
}
