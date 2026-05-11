package edu.uic.cs342.project2.backend.api;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uic.cs342.project2.backend.json.weather.objects.CurrentConditions;
import edu.uic.cs342.project2.backend.json.weather.objects.DailyForecasts;
import edu.uic.cs342.project2.backend.json.weather.objects.HourlyForecasts;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

import java.io.IOException;
import java.net.http.HttpResponse;

public final class WeatherAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final int HOURS = 24;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private WeatherAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static CurrentConditions requestCurrentWeather(double latitude, double longitude, UnitSystem unitSystem)
            throws IOException, InterruptedException, NullPointerException {
        if (unitSystem == null) {
            throw new NullPointerException("unitSystem is null");
        }

        String url = String.format(
                "https://weather.googleapis.com/v1/currentConditions:lookup?key=%s&location.latitude=%.4f&location.longitude=%.4f&unitsSystem=%s",
                APIKeys.GOOGLE_API_KEY, latitude, longitude, unitSystem.getName().toUpperCase()
        );

        InjectableValues injectableValues = new InjectableValues.Std().addValue(UnitSystem.class, unitSystem);
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);
        return WeatherAPI.OBJECT_MAPPER
                .setInjectableValues(injectableValues)
                .readValue(response.body(), CurrentConditions.class);
    }

    public static HourlyForecasts requestHourlyForecasts(double latitude, double longitude, UnitSystem unitSystem)
            throws IOException, InterruptedException, NullPointerException {
        if (unitSystem == null) {
            throw new NullPointerException("unitSystem is null");
        }

        String url = String.format(
                "https://weather.googleapis.com/v1/forecast/hours:lookup?key=%s&location.latitude=%.4f&location.longitude=%.4f&hours=%d&unitsSystem=%s",
                APIKeys.GOOGLE_API_KEY, latitude, longitude, WeatherAPI.HOURS, unitSystem.getName().toUpperCase()
        );

        InjectableValues injectableValues = new InjectableValues.Std().addValue(UnitSystem.class, unitSystem);
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);
        return WeatherAPI.OBJECT_MAPPER
                .setInjectableValues(injectableValues)
                .readValue(response.body(), HourlyForecasts.class);
    }

    public static DailyForecasts requestDailyForecasts(double latitude, double longitude, UnitSystem unitSystem)
            throws IOException, InterruptedException, NullPointerException {
        if (unitSystem == null) {
            throw new NullPointerException("unitSystem is null");
        }

        String url = String.format(
                "https://weather.googleapis.com/v1/forecast/days:lookup?key=%s&location.latitude=%.4f&location.longitude=%.4f&days=7&unitsSystem=%s",
                APIKeys.GOOGLE_API_KEY, latitude, longitude, unitSystem.getName().toUpperCase()
        );

        InjectableValues injectableValues = new InjectableValues.Std().addValue(UnitSystem.class, unitSystem);
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);
        return WeatherAPI.OBJECT_MAPPER
                .setInjectableValues(injectableValues)
                .readValue(response.body(), DailyForecasts.class);
    }
}
