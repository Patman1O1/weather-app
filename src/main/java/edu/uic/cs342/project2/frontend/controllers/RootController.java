package edu.uic.cs342.project2.frontend.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOut;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class RootController {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    private StackPane outlet;

    @FXML
    private NavigationController navController;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public RootController() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        this.navController.setOnNavigate(this::navigateTo);
        navigateTo(NavigationController.Route.CURRENT_WEATHER);
    }

    private void navigateTo(NavigationController.Route route) {
        String fxmlPath;
        switch (route) {
            case HOURLY: fxmlPath = "/fxml/hourly-forecast.fxml"; break;
            case DAILY: fxmlPath = "/fxml/daily-forecast.fxml"; break;
            case MAP: fxmlPath = "/fxml/map.fxml"; break;
            case LOCATIONS: fxmlPath = "/fxml/location.fxml"; break;
            case SETTINGS:  fxmlPath = "/fxml/settings.fxml"; break;
            default: fxmlPath = "/fxml/current-weather.fxml"; break;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();


            if (!this.outlet.getChildren().isEmpty()) {
                Parent oldView = (Parent) this.outlet.getChildren().get(0);

                FadeOut fadeOut = new FadeOut(oldView);
                fadeOut.setOnFinished(actionEvent -> {
                    this.outlet.getChildren().setAll(newView);
                    new FadeIn(newView).play();
                });
                fadeOut.play();
            } else {
                this.outlet.getChildren().setAll(newView);
                new FadeInUp(newView).play();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load: " + fxmlPath, e);
        }
    }
}
