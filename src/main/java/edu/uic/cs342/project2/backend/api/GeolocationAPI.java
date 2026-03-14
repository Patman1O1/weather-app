package edu.uic.cs342.project2.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uic.cs342.project2.backend.location.Coordinates;

import java.io.IOException;
import java.net.http.HttpResponse;

public final class GeolocationAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private GeolocationAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static Coordinates requestCoordinates() throws IOException, InterruptedException {
        String url = String.format("https://www.googleapis.com/geolocation/v1/geolocate?key=%s", APIKeys.GOOGLE_API_KEY);
        HttpResponse<String> response = HttpRequests.sendPOSTRequest(url, "{}");


        JsonNode locationNode = GeolocationAPI.OBJECT_MAPPER.readTree(response.body()).path("location");
        return new Coordinates(locationNode.get("lat").asDouble(), locationNode.get("lng").asDouble());
    }
}
