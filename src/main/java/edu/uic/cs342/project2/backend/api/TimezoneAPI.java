package edu.uic.cs342.project2.backend.api;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.location.Coordinates;

public final class TimezoneAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private TimezoneAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static ZonedDateTime requestDateTime(Coordinates coordinates)
        throws IOException, InterruptedException, RuntimeException {
        if (coordinates == null) {
            throw new NullPointerException("coordinates is null");
        }

        long epochSecond = Instant.now().getEpochSecond();
        String url = String.format("https://maps.googleapis.com/maps/api/timezone/json?location=%.4f,%.4f&timestamp=%d&key=%s",
                coordinates.getLatitude().doubleValue(),
                coordinates.getLongitude().doubleValue(),
                epochSecond,
                APIKeys.GOOGLE_API_KEY
        );
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);
        JsonNode rootNode = TimezoneAPI.OBJECT_MAPPER.readTree(response.body());
        if (!rootNode.get("status").asText().equals("OK")) {
            throw new RuntimeException("Failed retrieving timezone data");
        }

        return ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(rootNode.path("timeZoneId").asText()));
    }
}
