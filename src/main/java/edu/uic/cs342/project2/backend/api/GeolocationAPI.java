package edu.uic.cs342.project2.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;

import java.io.IOException;
import java.net.http.HttpResponse;

public final class GeolocationAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final String URL = String.format("https://www.googleapis.com/geolocation/v1/geolocate?key=%s", APIKeys.GOOGLE_API_KEY);

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private GeolocationAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static Coordinates requestCoordinates() throws IOException, InterruptedException {
        // Make a request to the API and store its response
        HttpResponse<String> response = HttpRequests.sendPOSTRequest(GeolocationAPI.URL, "{}");

        // Parse the JSON response into a Java object
        return new ObjectMapper().readValue(response.body(), Coordinates.class);
    }
}
