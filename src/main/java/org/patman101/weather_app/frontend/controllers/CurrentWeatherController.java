package org.patman101.weather_app.frontend.controllers;

import org.patman101.weather_app.backend.api.WeatherAPI;
import org.patman101.weather_app.backend.json.geocode.objects.AddressComponents;
import org.patman101.weather_app.backend.json.geocode.objects.Location;
import org.patman101.weather_app.backend.json.geolocation.objects.Coordinates;
import org.patman101.weather_app.backend.json.weather.objects.*;
import org.patman101.weather_app.frontend.ui.UserLocation;
import org.patman101.weather_app.frontend.ui.measurements.UnitSystem;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CurrentWeatherController {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final SettingsController.Settings settings;

    private final UserLocation userLocation;

    @FXML
    private ImageView iconView;

    @FXML
    private Label localityLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label highLabel;

    @FXML
    private Label lowLabel;

    @FXML
    private Label feelsLikeLabel;

    @FXML
    private Label windSpeedLabel;

    @FXML
    private Label windDirectionLabel;

    @FXML
    private Label windChillLabel;

    @FXML
    private Label visibilityLabel;

    @FXML
    private Label humidityLabel;

    @FXML
    private Label dewPointLabel;

    @FXML
    private Label uvIndexLabel;

    @FXML
    private Label cloudCoverLabel;

    @FXML
    private Label airPressureLabel;

    @FXML
    private Label precipQuantLabel;

    @FXML
    private Label precipProbLabel;

    @FXML
    private Label tstormProbLabel;

    // ── Constructors ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public CurrentWeatherController() throws IOException, InterruptedException {
        this.userLocation = UserLocation.getInstance();
        this.settings = SettingsController.Settings.getInstance();
        UnitSystem unitSystem = this.settings.getUnitSystem();
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setLocation() throws NullPointerException {
        Location location = this.userLocation.getLocation();
        AddressComponents addressComponents = location.getAddressComponents();
        boolean localityFound = false, countryFound = false;
        for (AddressComponents.AddressComponent addressComponent : addressComponents) {
            // Exit the function if both the locality and country labels have been set
            if (localityFound && countryFound) {
                return;
            }

            if (addressComponent.getTypes().contains("locality")) {
                // Set the locality label and update it's boolean variable
                this.localityLabel.setText(addressComponent.getLongName());
                localityFound = true;
            } else if (addressComponent.getTypes().contains("country")) {
                // Set the country label and update it's boolean variable
                this.countryLabel.setText(addressComponent.getShortName());
                countryFound = true;
            }
        }

        // TODO - handle the case where the locality or country are not found
    }

    public void setDateTime(ZoneId timeZoneId) throws NullPointerException {
        if (timeZoneId == null) {
            throw new NullPointerException("timeZoneId is null");
        }

        Timeline date = new Timeline(
                new KeyFrame(Duration.ZERO, actionEvent -> {
                    this.dateLabel.setText(LocalDateTime.now(timeZoneId)
                            .format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));
                }),
                new KeyFrame(Duration.hours(24.0))
        );
        date.setCycleCount(Timeline.INDEFINITE);
        date.play();

        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, actionEvent -> {
                    this.timeLabel.setText(LocalDateTime.now(timeZoneId)
                            .format(DateTimeFormatter.ofPattern("hh:mm a")));
                }),
                new KeyFrame(Duration.seconds(1.0))
        );
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    public void setTemperature(Temperature temperature) throws NullPointerException {
        if (temperature == null) {
            throw new NullPointerException("temperature is null");
        }
        this.temperatureLabel.setText(temperature.toString());
    }

    public void setFeelsLike(Temperature feelsLike) throws NullPointerException {
        if (feelsLike == null) {
            throw new NullPointerException("feelsLike is null");
        }
        this.feelsLikeLabel.setText(feelsLike.toString());
    }

    public void setHigh(Temperature high) throws NullPointerException {
        if (high == null) {
            throw new NullPointerException("high is null");
        }
        this.highLabel.setText(String.format("H: %s", high));
    }

    public void setLow(Temperature low) throws NullPointerException {
        if (low == null) {
            throw new NullPointerException("low is null");
        }
        this.lowLabel.setText(String.format("L: %s", low));
    }

    public void setConditionLabel(String condition) throws NullPointerException {
        if (condition == null) {
            throw new NullPointerException("condition is null");
        }
        this.conditionLabel.setText(condition);
    }

    public void setWind(Wind wind) throws NullPointerException {
        if (wind == null) {
            throw new NullPointerException("wind is null");
        }
        this.windSpeedLabel.setText(wind.getSpeed().toString());
        this.windDirectionLabel.setText(wind.getDirection().toString());
    }


    public void setWindChill(Temperature windChill) throws NullPointerException {
        if (windChill == null) {
            throw new NullPointerException("windChill is null");
        }
        this.windChillLabel.setText(windChill.toString());
    }


    public void setVisibility(Visibility visibility) throws NullPointerException {
        if (visibility == null) {
            throw new NullPointerException("visibility is null");
        }

        this.visibilityLabel.setText(visibility.toString());
    }

    public void setHumidity(double humidity) {
        this.humidityLabel.setText(String.format("%d%%", (int) Math.round(humidity)));
    }

    public void setDewPointLabel(Temperature dewPoint) throws NullPointerException {
        if (dewPoint == null) {
            throw new NullPointerException("dewPoint is null");
        }
        this.dewPointLabel.setText(dewPoint.toString());
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndexLabel.setText(String.format("%d", uvIndex));
    }

    public void setCloudCoverLabel(double cloudCover) {
        this.cloudCoverLabel.setText(String.format("%d%%", (int) Math.round(cloudCover)));
    }

    public void setAirPressure(double airPressure) { this.airPressureLabel.setText(String.valueOf(airPressure)); }

    public void setPrecipitation(Precipitation precipitation) throws NullPointerException {
        if (precipitation == null) {
            throw new NullPointerException("precipitation is null");
        }

        this.precipQuantLabel.setText(precipitation.getQpf().toString());
        this.precipProbLabel.setText(String.format("%d%%", (int) Math.round(precipitation.getProbability().percent)));
    }

    public void setTstormProbability(double tstormProb) {
        this.tstormProbLabel.setText(String.format("%d%%", (int) Math.round(tstormProb)));
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    public void initialize() throws IOException, InterruptedException {
        // Get the current weather at the user's location
        Coordinates coordinates = this.userLocation.getCoordinates();
        CurrentConditions currentWeather = WeatherAPI.requestCurrentWeather(
                coordinates.latitude,
                coordinates.longitude,
                this.settings.getUnitSystem());

        WeatherCondition currentCondition = currentWeather.getCondition();

        CurrentConditions.History history = currentWeather.getHistory();

        // Set the weather icon
        this.iconView.setImage(new Image(currentCondition.getIconPath()));

        // Display the user's location
        this.setLocation();

        // Display the current date and time
        this.setDateTime(currentWeather.getTimeZoneId());

        // Display the temperature
        this.setTemperature(currentWeather.getTemperature());

        // Display feels-like temperature
        this.setFeelsLike(currentWeather.getFeelsLikeTemperature());

        // Display high
        this.setHigh(history.getMaxTemperature());

        // Display low
        this.setLow(history.getMinTemperature());

        // Display condition
        this.setConditionLabel(currentCondition.getDescription());

        // Display wind
        this.setWind(currentWeather.getWind());

        // Display wind chill
        this.setWindChill(currentWeather.getWindChill());

        // Display the visibility
        this.setVisibility(currentWeather.getVisibility());

        // Display the humidity
        this.setHumidity(currentWeather.relativeHumidity);

        // Display the dew point
        this.setDewPointLabel(currentWeather.getDewPoint());

        // Display the UV index
        this.setUvIndex(currentWeather.uvIndex);

        // Display the cloud coverage
        this.setCloudCoverLabel(currentWeather.cloudCover);

        // Display the air pressure
        this.setAirPressure(currentWeather.airPressure);

        // Display precipitation
        this.setPrecipitation(currentWeather.getPrecipitation());


        // Display thunderstorm probability
        this.setTstormProbability(currentWeather.thunderstormProbability);
    }
}
