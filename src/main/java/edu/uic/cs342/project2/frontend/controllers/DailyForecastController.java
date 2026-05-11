package edu.uic.cs342.project2.frontend.controllers;

import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeIn;

import atlantafx.base.theme.Styles;

import edu.uic.cs342.project2.backend.api.WeatherAPI;
import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;
import edu.uic.cs342.project2.backend.json.weather.objects.DailyForecasts;
import edu.uic.cs342.project2.backend.json.weather.objects.Precipitation;
import edu.uic.cs342.project2.backend.json.weather.objects.Temperature;
import edu.uic.cs342.project2.backend.json.weather.objects.Wind;
import edu.uic.cs342.project2.frontend.ui.UserLocation;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.util.Duration;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class DailyForecastController {
    // ── DayRow ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static class DayRow extends HBox {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private Label dayLabel;

        private HBox daytimeCondBox, nighttimeCondBox, temperatureBox;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public DayRow(ZoneId zoneId, DailyForecasts.DailyForecast forecast) throws NullPointerException {
            this.setDayLabel(zoneId, forecast);
            this.setDaytimeCondBox(forecast);
            this.setNighttimeCondBox(forecast);
            this.setTemperatureBox(forecast);

            super.getChildren().addAll(this.dayLabel, this.daytimeCondBox, this.nighttimeCondBox, this.temperatureBox);
            super.getStyleClass().add("day-row");
            super.setAlignment(Pos.CENTER_LEFT);
            super.setCursor(Cursor.HAND);

            // Show details popup
            super.setOnMouseClicked(event -> showDetails(forecast, zoneId));

            // Hover highlight
            super.setOnMouseEntered(event ->
                    setStyle("-fx-background-color: -color-bg-subtle; -fx-background-radius: 8;"));
            super.setOnMouseExited(event -> setStyle(""));
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        private void setDayLabel(ZoneId zoneId, DailyForecasts.DailyForecast forecast) {
            ZonedDateTime dateTime = forecast.getDaytimeForecast()
                    .getInterval().getStartTime()
                    .withZoneSameInstant(zoneId);

            String text = dateTime.getDayOfWeek()
                    .equals(ZonedDateTime.now().getDayOfWeek())
                    ? "Today"
                    : dateTime.format(DateTimeFormatter.ofPattern("EEEE"));

            this.dayLabel = new Label(text);
            this.dayLabel.getStyleClass().addAll("day-label", Styles.TEXT_BOLD);
            this.dayLabel.setMinWidth(90.0);
        }

        private void setDaytimeCondBox(DailyForecasts.DailyForecast forecast) {
            ImageView icon = createIcon(forecast.getDaytimeForecast().getCondition().getIconPath());

            Label description = new Label(forecast.getDaytimeForecast().getCondition().getDescription());
            description.getStyleClass().add(Styles.TEXT_MUTED);
            description.setStyle("-fx-font-size: 12px;");

            Label label = new Label("Day");
            label.setStyle(
                    "-fx-background-color: -color-accent-subtle;" +
                            "-fx-text-fill: -color-accent-fg;" +
                            "-fx-background-radius: 4; -fx-padding: 1 5;" +
                            "-fx-font-size: 10px;");

            VBox labelBox = new VBox(2.0, label, description);
            this.daytimeCondBox = new HBox(8.0, icon, labelBox);
            this.daytimeCondBox.setAlignment(Pos.CENTER_LEFT);
            this.daytimeCondBox.setMinWidth(160.0);
        }

        private void setNighttimeCondBox(DailyForecasts.DailyForecast forecast) {
            ImageView icon = createIcon(forecast.getNighttimeForecast().getCondition().getIconPath());

            Label description = new Label(
                    forecast.getNighttimeForecast().getCondition().getDescription());
            description.getStyleClass().add(Styles.TEXT_MUTED);
            description.setStyle("-fx-font-size: 12px;");

            Label label = new Label("Night");
            label.setStyle(
                    "-fx-background-color: -color-neutral-subtle;" +
                            "-fx-text-fill: -color-fg-muted;" +
                            "-fx-background-radius: 4; -fx-padding: 1 5;" +
                            "-fx-font-size: 10px;");

            VBox labelBox = new VBox(2.0, label, description);
            this.nighttimeCondBox = new HBox(8.0, icon, labelBox);
            this.nighttimeCondBox.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(nighttimeCondBox, Priority.ALWAYS);
            this.nighttimeCondBox.setMinWidth(160.0);
        }

        private void setTemperatureBox(DailyForecasts.DailyForecast forecast) {
            VBox highTempBox = createTempCol("High", forecast.getMaxTemperature(),
                    "-color-danger-fg");

            VBox lowTempBox = createTempCol("Low", forecast.getMinTemperature(),
                    "-color-accent-fg");

            this.temperatureBox = new HBox(12.0, highTempBox, lowTempBox);
            this.temperatureBox.setAlignment(Pos.CENTER_RIGHT);
        }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        private static VBox buildDetailCard(DailyForecasts.DailyForecast forecast, ZoneId zoneId, Popup popup) {
            // Create date time label
            ZonedDateTime dateTime = forecast.getDaytimeForecast()
                    .getInterval().getStartTime()
                    .withZoneSameInstant(zoneId);

            Label dateLabel = new Label(dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d")));
            dateLabel.getStyleClass().add("section-title");

            // Create close button
            Button closeButton = new Button("✕");
            closeButton.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-text-fill: -color-fg-muted;" +
                            "-fx-cursor: hand; -fx-font-size: 13px;");
            closeButton.setOnAction(event -> popup.hide());

            // Create divider between date time and day/night sections
            Region headerSpacer = new Region();
            HBox.setHgrow(headerSpacer, Priority.ALWAYS);
            HBox headerRow = new HBox(dateLabel, headerSpacer, closeButton);
            headerRow.setAlignment(Pos.CENTER_LEFT);

            // Create day section
            VBox daySection = DayRow.buildPeriodSection(
                    "Daytime",
                    forecast.getDaytimeForecast().getCondition().getIconPath(),
                    forecast.getDaytimeForecast().getCondition().getDescription(),
                    forecast.getMaxTemperature(),
                    forecast.getMinTemperature(),
                    forecast.getDaytimeForecast()
            );

            // Create night section
            VBox nightSection = DayRow.buildPeriodSection(
                    "Nighttime",
                    forecast.getNighttimeForecast().getCondition().getIconPath(),
                    forecast.getNighttimeForecast().getCondition().getDescription(),
                    forecast.getMaxTemperature(),
                    forecast.getMinTemperature(),
                    forecast.getNighttimeForecast()
            );

            // Assemble and build card
            VBox card = new VBox(20.0, headerRow, new HBox(20.0, new HBox(20.0, daySection, nightSection)));
            card.setStyle(
                    "-fx-background-color: -color-bg-default;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: -color-border-default;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 20;" +
                            "-fx-padding: 24;" +
                            "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.18),20,0,0,6);"
            );

            return card;
        }

        private static VBox buildPeriodSection(String title,
                                               String iconPath,
                                               String description,
                                               Temperature high,
                                               Temperature low,
                                               DailyForecasts.Forecast periodForecast) {
            Label titleLabel = new Label(title);
            titleLabel.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: 600;" +
                            "-fx-text-fill: -color-fg-muted;");

            ImageView iconView = new ImageView();
            iconView.setFitWidth(64.0);
            iconView.setFitHeight(64.0);
            iconView.setPreserveRatio(true);
            iconView.setImage(new Image(iconPath));

            Label highLabel = new Label(high != null ? high.toString() : "--");
            highLabel.setStyle(
                    "-fx-font-size: 40px; -fx-font-weight: 300;" +
                            "-fx-text-fill: -color-fg-default;");

            Label condLabel = new Label(description);
            condLabel.setStyle(
                    "-fx-font-size: 15px; -fx-text-fill: -color-fg-subtle;");

            Label highLowLabel = new Label(
                    String.format("H: %s   L: %s",
                            high != null ? high.toString() : "--",
                            low  != null ? low.toString()  : "--"));
            highLowLabel.getStyleClass().add(Styles.TEXT_MUTED);

            VBox tempBox = new VBox(4.0, highLabel, condLabel, highLowLabel);

            Region mainSpacer = new Region();
            HBox.setHgrow(mainSpacer, Priority.ALWAYS);
            HBox mainRow = new HBox(24.0, iconView, tempBox, mainSpacer);
            mainRow.setAlignment(Pos.CENTER_LEFT);

            // Build stats section matching the current weather card layout
            VBox statsSection = DayRow.buildStatsSection(periodForecast);

            VBox section = new VBox(12.0, titleLabel, mainRow, statsSection);
            section.setStyle(
                    "-fx-background-color: -color-bg-subtle;" +
                            "-fx-background-radius: 14;" +
                            "-fx-padding: 16;");

            return section;
        }

        private static VBox buildStatsSection(DailyForecasts.Forecast forecast) {
            // Build wind stats
            Wind wind = forecast.getWind();
            VBox windGroup = buildStatGroup("WIND",
                    statBox("Speed", wind.getSpeed().toString()),
                    statBox("Direction", wind.getDirection().getName())
            );

            Region div1 = new Region();
            div1.getStyleClass().add("stat-group-divider");

            // Build atmosphere stats
            VBox atmosphereGroup = buildStatGroup("ATMOSPHERE",
                    statBox("Humidity", String.format("%d%%", (int) Math.round(forecast.relativeHumidity))),
                    statBox("UV Index", String.valueOf(forecast.uvIndex))
            );

            Region div2 = new Region();
            div2.getStyleClass().add("stat-group-divider");

            // Build precipitation stats
            Precipitation precipitation = forecast.getPrecipitation();
            VBox precipGroup = buildStatGroup("PRECIPITATION",
                    statBox("Quantity", precipitation.getQpf().toString()),
                    statBox("Probability", String.format("%d%%", (int) Math.round(precipitation.getProbability().percent)))
            );

            VBox container = new VBox(windGroup, div1, atmosphereGroup, div2, precipGroup);
            container.getStyleClass().add("stats-container");
            return container;
        }

        private static VBox buildStatGroup(String title, VBox... statBoxes) {
            Label groupTitle = new Label(title);
            groupTitle.getStyleClass().add("stat-group-title");

            HBox row = new HBox();
            for (int i = 0; i < statBoxes.length; i++) {
                HBox.setHgrow(statBoxes[i], Priority.ALWAYS);
                row.getChildren().add(statBoxes[i]);
                if (i < statBoxes.length - 1) {
                    Region divider = new Region();
                    divider.getStyleClass().add("stat-divider");
                    row.getChildren().add(divider);
                }
            }

            VBox group = new VBox(groupTitle, row);
            group.getStyleClass().add("stat-group");
            return group;
        }

        private static VBox statBox(String title, String value) {
            Label titleLabel = new Label(title);
            titleLabel.getStyleClass().add("stat-label");

            Label valueLabel = new Label(value);
            valueLabel.getStyleClass().add("stat-value");

            VBox box = new VBox(titleLabel, valueLabel);
            box.getStyleClass().add("stat-box");
            return box;
        }

        private static VBox createTempCol(String title, Temperature temp, String color) {
            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: -color-fg-subtle;");

            Label valueLabel = new Label(temp != null ? temp.toString() : "--");
            valueLabel.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold;" +
                            "-fx-text-fill: " + color + ";");

            VBox tempCol = new VBox(2.0, titleLabel, valueLabel);
            tempCol.setAlignment(Pos.CENTER_RIGHT);
            tempCol.setMinWidth(50.0);
            return tempCol;
        }

        private static ImageView createIcon(String iconPath) {
            ImageView iconView = new ImageView();
            iconView.setFitWidth(28.0);
            iconView.setFitHeight(28.0);
            iconView.setPreserveRatio(true);
            iconView.setImage(new Image(iconPath));
            return iconView;
        }

        private void showDetails(DailyForecasts.DailyForecast forecast, ZoneId zoneId) {
            Popup popup = new Popup();
            popup.setAutoHide(true);
            popup.setAutoFix(true);

            VBox card = buildDetailCard(forecast, zoneId, popup);
            popup.getContent().add(card);

            Bounds bounds = localToScreen(getBoundsInLocal());
            if (bounds != null) {
                popup.show(getScene().getWindow(), bounds.getMinX(), bounds.getMaxY() + 8.0);
            }

            new FadeIn(card).play();
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final UserLocation userLocation;

    private final SettingsController.Settings settings;

    @FXML
    private VBox forecastContainer;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public DailyForecastController() throws IOException, InterruptedException {
        this.userLocation = UserLocation.getInstance();
        this.settings = SettingsController.Settings.getInstance();
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private void renderDaily(DailyForecasts dailyForecasts) {
        this.forecastContainer.getChildren().clear();
        Iterator<DailyForecasts.DailyForecast> iterator = dailyForecasts.iterator();
        ZoneId zoneId = dailyForecasts.getZoneId();

        for (int i = 0; iterator.hasNext(); ++i) {
            DayRow row = new DayRow(zoneId, iterator.next());
            this.forecastContainer.getChildren().add(row);

            FadeInLeft animation = new FadeInLeft(row);
            animation.setDelay(Duration.millis(i * 60.0));
            animation.play();
        }
    }

    @FXML
    public void initialize() throws IOException, InterruptedException {
        Coordinates coordinates = this.userLocation.getCoordinates();
        DailyForecasts forecasts = WeatherAPI.requestDailyForecasts(coordinates.latitude, coordinates.longitude, this.settings.getUnitSystem());
        this.renderDaily(forecasts);
    }
}