package edu.uic.cs342.project2.backend.api;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.location.Coordinates;
import edu.uic.cs342.project2.backend.location.DaylightInfo;

public final class SunriseSunsetAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private SunriseSunsetAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static DaylightInfo requestDaylightInfo(Coordinates coordinates)
            throws IOException, InterruptedException, RuntimeException {
        if (coordinates == null) {
            throw new NullPointerException("coordinates is null");
        }

        // Prepare the URL to send to the API
        String url = String.format(
                "https://api.sunrise-sunset.org/json?lat=%.4f&lng=%.4f&formatted=0",
                coordinates.getLatitude().doubleValue(),
                coordinates.getLongitude().doubleValue()
        );

        // Query the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Deserialize the response
        JsonNode rootNode = SunriseSunsetAPI.OBJECT_MAPPER.readTree(response.body());
        if (!rootNode.path("status").asText().equals("OK")) {
            throw new RuntimeException("Failed to request daylight info");
        }
        JsonNode resultsNode = rootNode.path("results");
        LocalTime sunriseTime = OffsetDateTime.parse(resultsNode.path("sunrise").asText()).toLocalTime();
        LocalTime sunsetTime = OffsetDateTime.parse(resultsNode.path("sunset").asText()).toLocalTime();

        return new DaylightInfo(sunriseTime, sunsetTime);
    }
}
