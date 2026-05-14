package edu.uic.cs342.project2.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uic.cs342.project2.backend.json.geocode.objects.Location;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public final class GeocodeAPI {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private GeocodeAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static Location requestLocation(double latitude, double longitude) throws IOException, InterruptedException {
        // Prepare the URL to send to the API
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s",
                latitude,
                longitude,
                APIKeys.GOOGLE_API_KEY
        );

        // Send the request to the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Deserialize the response and return the results
        return new ObjectMapper().readValue(response.body(), Location.class);
    }

    public static Location requestLocation(String address)
            throws IOException, InterruptedException, NullPointerException {
        if (address == null) {
            throw new NullPointerException("address is null");
        }

        // Replace every whitespace character in the address with '+'
        String formattedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

        // Prepare the URL
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?address=%s?&key=%s",
                formattedAddress,
                APIKeys.GOOGLE_API_KEY
        );

        // Send the request to the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Deserialize the information
        return new ObjectMapper().readValue(response.body(), Location.class);
    }
}
