package edu.uic.cs342.project2.frontend.controllers;

import animatefx.animation.FadeInUp;

import atlantafx.base.theme.Styles;

import edu.uic.cs342.project2.backend.api.WeatherAPI;
import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;
import edu.uic.cs342.project2.backend.json.weather.objects.HourlyForecasts;
import edu.uic.cs342.project2.frontend.ui.UserLocation;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class HourlyForecastController {
    // ── Hour Card ────────────────────────────────────────────────────────────────────────────────────────────────────
    private static class HourCard extends VBox {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private VBox iconContainer;

        private Label timeLabel, tempLabel;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public HourCard(ZoneId zoneId, HourlyForecasts.Forecast hourlyForecast) throws NullPointerException {
            if (hourlyForecast == null) {
                throw new NullPointerException("hourlyForecast is null");
            }

            this.setTimeLabel(zoneId, hourlyForecast);
            this.setIcon(hourlyForecast);
            this.setTempLabel(hourlyForecast);

            super.getChildren().addAll(this.timeLabel, this.iconContainer, this.tempLabel);
            super.setSpacing(8.0);

            super.getStyleClass().addAll("hour-card", Styles.ELEVATED_1);
            super.setAlignment(Pos.CENTER);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        private void setTimeLabel(ZoneId zoneId, HourlyForecasts.Forecast hourlyForecast) {
            // Get the datetime and timezone ID
            ZonedDateTime dateTime = hourlyForecast.getInterval().getStartTime();
            ZonedDateTime localDatetime = dateTime.withZoneSameInstant(zoneId);

            // Set the label with information
            if (localDatetime.getHour() != ZonedDateTime.now().getHour()) {
                this.timeLabel = new Label(localDatetime.format(DateTimeFormatter.ofPattern("hh a")));
            } else {
                this.timeLabel = new Label("Now");
            }
        }

        private void setIcon(HourlyForecasts.Forecast hourlyForecast) {
            ImageView iconView = new ImageView();
            iconView.setFitHeight(32.0);
            iconView.setFitWidth(32.0);
            iconView.setImage(new Image(hourlyForecast.getWeatherCondition().getIconPath()));

            this.iconContainer = new VBox();
            this.iconContainer.getChildren().add(iconView);
            this.iconContainer.setAlignment(Pos.CENTER);
        }

        private void setTempLabel(HourlyForecasts.Forecast hourlyForecast) {
            this.tempLabel = new Label(hourlyForecast.getTemperature().toString());
            this.tempLabel.getStyleClass().addAll("hour-temp", Styles.TEXT_BOLD);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final UserLocation userLocation;

    private final SettingsController.Settings settings;

    @FXML
    private HBox hourlyContainer;

    @FXML
    private ScrollPane scrollPane;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public HourlyForecastController() throws IOException, InterruptedException {
        this.userLocation = UserLocation.getInstance();
        this.settings = SettingsController.Settings.getInstance();
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private void renderHourly(HourlyForecasts hourlyForecasts) {
        this.hourlyContainer.getChildren().clear();
        Iterator<HourlyForecasts.Forecast> iterator = hourlyForecasts.iterator();
        ZoneId zoneId = hourlyForecasts.getZoneId();
        for (int i = 0; iterator.hasNext(); i++) {
            HourCard hourCard = new HourCard(zoneId, iterator.next());
            FadeInUp animation = new FadeInUp(hourCard);

            this.hourlyContainer.getChildren().add(hourCard);
            animation.setDelay(Duration.millis(i * 50));
            animation.play();
        }
    }


    @FXML
    public void initialize() throws IOException, InterruptedException {
        Coordinates coordinates = this.userLocation.getCoordinates();
        HourlyForecasts hourlyForecasts = WeatherAPI.requestHourlyForecasts(
                coordinates.latitude,
                coordinates.longitude,
                this.settings.getUnitSystem());
        this.renderHourly(hourlyForecasts);
    }
}
