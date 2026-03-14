package edu.uic.cs342.project2.backend.api;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.location.Coordinates;
import edu.uic.cs342.project2.backend.measurements.UnitSystem;
import edu.uic.cs342.project2.backend.weather.Weather;

public final class WeatherAPI {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private WeatherAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static Weather requestCurrentWeather(Coordinates coordinates, UnitSystem unitSystem)
            throws IOException, InterruptedException, NullPointerException {
        if (coordinates == null) {
            throw new NullPointerException("coordinates is null");
        }

        if (unitSystem == null) {
            throw new NullPointerException("unitSystem is null");
        }

        // Prepare the URL to send to the API
        String unitSystemName = unitSystem.getName();
        String url = String.format(
                "https://weather.googleapis.com/v1/currentConditions:lookup?key=%s&location.latitude=%.4f&location.longitude=%.4f&unitsSystem=%s",
                APIKeys.GOOGLE_API_KEY,
                coordinates.getLatitude().doubleValue(),
                coordinates.getLongitude().doubleValue(),
                unitSystemName.equals("metric") ? "METRIC" : "IMPERIAL"
        );

        // Create an object mapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Query the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Inject the coordinates and unit system
        InjectableValues injectableValues = new InjectableValues.Std()
                .addValue(UnitSystem.class, unitSystem);
        objectMapper.setInjectableValues(injectableValues);

        // Parse the JSON response
        return objectMapper.readValue(response.body(), Weather.class);
    }
}
