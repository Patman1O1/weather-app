package edu.uic.cs342.project2.frontend.controllers;

import animatefx.animation.FadeIn;

import atlantafx.base.controls.ToggleSwitch;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystems;
import edu.uic.cs342.project2.frontend.ui.themes.Theme;
import edu.uic.cs342.project2.frontend.ui.themes.Themes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsController {
    // ── Settings ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Settings {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private static final Path DIRECTORY = Paths.get(System.getProperty("user.dir"), ".local");

        private static final File FILE = Path.of(Settings.DIRECTORY.toString(), "settings.json").toFile();

        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        private static final Theme DEFAULT_THEME = Themes.LIGHT;

        private static final UnitSystem DEFAULT_UNIT_SYSTEM = UnitSystems.IMPERIAL;

        private static Settings instance;

        private final ObjectProperty<UnitSystem> unitSystemProperty;

        private final ObjectProperty<Theme> themeProperty;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        private Settings(UnitSystem unitSystem, Theme theme) {
            this.unitSystemProperty = new SimpleObjectProperty<>(unitSystem);
            this.themeProperty = new SimpleObjectProperty<>(theme);

            // Bug 4 fix — listener only applies side effects, never calls
            // setUnitSystem/setTheme again to avoid recursion
            this.unitSystemProperty.addListener((observableValue, oldUnitSystem, newUnitSystem) -> {
                this.save();
            });

            this.themeProperty.addListener((observableValue, oldTheme, newTheme) -> {
                newTheme.apply();
                this.save();
            });

            theme.apply();
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setUnitSystem(UnitSystem unitSystem) throws NullPointerException {
            if (unitSystem == null) {
                throw new NullPointerException("unitSystem is null");
            }
            // Guard: only set if actually changed to avoid spurious listener fires
            if (!unitSystem.equals(this.unitSystemProperty.get())) {
                this.unitSystemProperty.set(unitSystem);
            }
        }

        public void setTheme(Theme theme) throws NullPointerException {
            if (theme == null) {
                throw new NullPointerException("theme is null");
            }

            // Guard: only set if actually changed
            if (!theme.equals(this.themeProperty.get())) {
                this.themeProperty.set(theme);
            }
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        // Bug 5 fix — synchronized on method is sufficient, volatile removed
        public static synchronized Settings getInstance() throws IOException {
            if (Settings.instance == null) {
                Settings.load();
            }
            return Settings.instance;
        }

        public UnitSystem getUnitSystem() { return this.unitSystemProperty.get(); }

        public Theme getTheme() { return this.themeProperty.get(); }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        private static void load() {
            try {
                if (Settings.FILE.exists()) {
                    JsonNode root = Settings.OBJECT_MAPPER.readTree(Settings.FILE);

                    UnitSystem unitSystem = root.path("unitSystem").asText().equals("imperial")
                            ? UnitSystems.IMPERIAL
                            : UnitSystems.METRIC;

                    Theme theme = root.path("theme").asText().equals("dark")
                            ? Themes.DARK
                            : Themes.LIGHT;

                    Settings.instance = new Settings(unitSystem, theme);
                } else {
                    System.err.println("No settings file found — using defaults");

                    Settings.instance = new Settings(Settings.DEFAULT_UNIT_SYSTEM, Settings.DEFAULT_THEME);
                    Files.createDirectories(Settings.DIRECTORY);
                    Files.createFile(Settings.FILE.toPath());
                }
            } catch (IOException ex) {
                Utilities.printException(IOException.class, ex.getMessage());
                System.err.println("Failed to load settings — using defaults");

                Settings.instance = new Settings(Settings.DEFAULT_UNIT_SYSTEM, Settings.DEFAULT_THEME);
            }
        }

        public void save() {
            try (JsonGenerator jsonGenerator = Settings.OBJECT_MAPPER.createGenerator(Settings.FILE, JsonEncoding.UTF8)) {
                // Start writing to the JSON file
                jsonGenerator.writeStartObject();

                // Save the unit system
                jsonGenerator.writeObjectField("unitSystem", this.unitSystemProperty.get().toString());

                // Save the theme
                jsonGenerator.writeObjectField("theme", this.themeProperty.get().toString());

                // Stop writing to the JSON file
                jsonGenerator.writeEndObject();
            } catch (IOException exception) {
                Utilities.printException(IOException.class, exception.getMessage());
            }
        }
    }

    // ── Fields ─────────────────────────────────────────────────────────────────────────────────────────────────────
    private final Settings settings;

    private final ToggleSwitch themeSwitch = new ToggleSwitch();

    private final ToggleSwitch unitsSwitch = new ToggleSwitch();

    // Bug 3 fix — flags prevent the switch→property→switch feedback loop
    private boolean updatingTheme = false;

    private boolean updatingUnits = false;

    @FXML
    private ImageView themeIconView;

    @FXML
    private Label themeDesc;

    @FXML
    private HBox themeToggleContainer;

    @FXML
    private HBox themeRow;

    @FXML
    private Label unitSystemDesc;

    @FXML
    private HBox unitSystemToggleContainer;

    @FXML
    private HBox unitSystemRow;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public SettingsController() throws IOException { this.settings = Settings.getInstance(); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        // Insert ControlsFX ToggleSwitches between the labels
        themeToggleContainer.getChildren().add(1, themeSwitch);
        unitSystemToggleContainer.getChildren().add(1, unitsSwitch);

        setupThemeToggle();
        setupUnitsToggle();

        // AnimateFX — fade in each row
        new FadeIn(themeRow).play();
        new FadeIn(unitSystemRow).play();
    }

    private void setupThemeToggle() {
        // Set initial display and switch state
        updateThemeDisplay();
        updatingTheme = true;
        themeSwitch.setSelected(settings.getTheme().equals(Themes.DARK));
        updatingTheme = false;

        // Bug 1 fix — correctly maps switch state to DARK or LIGHT
        // Bug 3 fix — guard flag prevents re-entry from property listener
        themeSwitch.selectedProperty().addListener((obs, old, on) -> {
            if (updatingTheme) return;
            settings.setTheme(on ? Themes.DARK : Themes.LIGHT);
        });

        // Sync switch back if settings change externally
        settings.themeProperty.addListener((obs, oldTheme, newTheme) -> {
            updateThemeDisplay();
            boolean shouldBeOn = newTheme.equals(Themes.DARK);
            if (themeSwitch.isSelected() != shouldBeOn) {
                updatingTheme = true;
                themeSwitch.setSelected(shouldBeOn);
                updatingTheme = false;
            }
        });
    }

    private void setupUnitsToggle() {
        // Set initial display and switch state
        updateUnitsDisplay();
        updatingUnits = true;
        unitsSwitch.setSelected(settings.getUnitSystem().equals(UnitSystems.IMPERIAL));
        updatingUnits = false;

        // Bug 2 fix — correctly maps switch state to IMPERIAL or METRIC
        // Bug 3 fix — guard flag prevents re-entry from property listener
        unitsSwitch.selectedProperty().addListener((obs, old, on) -> {
            if (updatingUnits) return;
            settings.setUnitSystem(on ? UnitSystems.IMPERIAL : UnitSystems.METRIC);
        });

        // Sync switch back if settings change externally
        settings.unitSystemProperty.addListener((obs, oldUnit, newUnit) -> {
            updateUnitsDisplay();
            boolean shouldBeOn = newUnit.equals(UnitSystems.IMPERIAL);
            if (unitsSwitch.isSelected() != shouldBeOn) {
                updatingUnits = true;
                unitsSwitch.setSelected(shouldBeOn);
                updatingUnits = false;
            }
        });
    }

    private void updateThemeDisplay() {
        Theme theme = this.settings.getTheme();
        this.themeIconView.setImage(new Image(theme.getIconPath()));
        this.themeDesc.setText(theme.equals(Themes.DARK) ? "Dark Mode" : "Light Mode");
    }

    private void updateUnitsDisplay() {
        boolean imp = settings.getUnitSystem().equals(UnitSystems.IMPERIAL);
        unitSystemDesc.setText(imp ? "Fahrenheit, mph, miles" : "Celsius, km/h, km");
    }
}