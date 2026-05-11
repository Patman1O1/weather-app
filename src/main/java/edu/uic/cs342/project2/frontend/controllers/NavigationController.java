package edu.uic.cs342.project2.frontend.controllers;

import atlantafx.base.theme.Styles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.function.Consumer;

public class NavigationController {
    // ── Route ────────────────────────────────────────────────────────────────────────────────────────────────────────
    public static enum Route {
        // ── Constants ────────────────────────────────────────────────────────────────────────────────────────────────
        CURRENT_WEATHER,
        HOURLY,
        DAILY,
        MAP,
        LOCATIONS,
        SETTINGS;

        @Override
        public String toString() {
            switch (this) {
                case CURRENT_WEATHER: return "Current";
                case HOURLY: return "Hourly";
                case DAILY: return "Daily";
                case MAP: return "Map";
                case LOCATIONS: return "Locations";
                case SETTINGS: return "Settings";
                default: return "Unknown";
            }
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    private ToggleButton currentWeatherButton;

    @FXML
    private ToggleButton hourlyForecastButton;

    @FXML
    private ToggleButton dailyForecastButton;

    @FXML
    private ToggleButton mapButton;

    @FXML
    private ToggleButton locationsButton;

    @FXML
    private ToggleButton settingsButton;

    private final ToggleGroup navigationGroup;

    private Consumer<Route> onNavigate;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public NavigationController() { this.navigationGroup = new ToggleGroup(); }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setOnNavigate(Consumer<Route> handler) throws NullPointerException {
        if (handler == null) {
            throw new NullPointerException("handler is null");
        }
        this.onNavigate = handler;
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private Route routeFor(ToggleButton toggleButton) {
        if (toggleButton == this.currentWeatherButton) {
            return Route.CURRENT_WEATHER;
        }

        if (toggleButton == this.hourlyForecastButton) {
            return Route.HOURLY;
        }

        if (toggleButton == this.dailyForecastButton) {
            return Route.DAILY;
        }

        if (toggleButton == this.mapButton) {
            return Route.MAP;
        }

        if (toggleButton == this.locationsButton) {
            return Route.LOCATIONS;
        }

        if (toggleButton == this.settingsButton) {
            return Route.SETTINGS;
        }

        return Route.CURRENT_WEATHER;
    }

    @FXML
    private void handleNavigation(ActionEvent actionEvent) {
        if (this.onNavigate == null) {
            return;
        }

        ToggleButton button = (ToggleButton) actionEvent.getSource();

        Route route = null;
        if (button == this.currentWeatherButton) {
            route = Route.CURRENT_WEATHER;
        }

        if (button == this.hourlyForecastButton) {
            route = Route.HOURLY;
        }

        if (button == this.dailyForecastButton) {
            route = Route.DAILY;
        }

        if (button == this.mapButton) {
            route = Route.MAP;
        }

        if (button == this.locationsButton) {
            route = Route.LOCATIONS;
        }

        if (button == this.settingsButton) {
            route = Route.SETTINGS;
        }

        if (route != null) {
            this.onNavigate.accept(route);
        }
    }

    @FXML
    public void initialize() {
        List<ToggleButton> buttons = List.of(
                this.currentWeatherButton, this.hourlyForecastButton, this.dailyForecastButton,
                this.mapButton, this.locationsButton, this.settingsButton
        );

        for (ToggleButton button : buttons) {
            button.setToggleGroup(this.navigationGroup);
            button.getStyleClass().addAll(Styles.ROUNDED, "nav-button");
        }

        this.navigationGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                oldToggle.setSelected(true);
            } else {
                if (this.onNavigate != null) {
                    this.onNavigate.accept(routeFor((ToggleButton) newToggle));
                }
            }
        });

        this.currentWeatherButton.setSelected(true);
    }
}
