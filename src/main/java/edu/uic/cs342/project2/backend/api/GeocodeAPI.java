package edu.uic.cs342.project2.backend.api;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uic.cs342.project2.backend.location.Coordinates;
import edu.uic.cs342.project2.backend.location.Location;

public final class GeocodeAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private GeocodeAPI() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static Location requestLocation(Coordinates coordinates)
            throws NullPointerException, IOException, InterruptedException {
        if (coordinates == null) {
            throw new NullPointerException("coordinates is null");
        }

        // Prepare the URL to send to the API
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s",
                coordinates.getLatitude().doubleValue(),
                coordinates.getLongitude().doubleValue(),
                APIKeys.GOOGLE_API_KEY
        );

        // Query the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Deserialize the JSON response
        Location location = new Location(coordinates);
        JsonNode rootNode = GeocodeAPI.OBJECT_MAPPER.readTree(response.body());
        for (JsonNode componentNode : rootNode.path("results").get(0).path("address_components")) {
            String name = componentNode.path("long_name").asText();
            JsonNode types = componentNode.path("types");

            for (JsonNode type : types) {
                String typeText = type.asText();
                switch (typeText) {
                    case "locality":
                        location.setName(name);
                        break;
                    case "administrative_area_level_1":
                        location.setRegion(name);
                        break;
                    case "country":
                        location.setCountry(name);
                        break;
                }
            }
        }
        return location;
    }

    public static Coordinates requestCoordinates(String locationName)
        throws NullPointerException, IOException, InterruptedException {
        if (locationName == null) {
            throw new NullPointerException("locationName is null");
        }

        // Prepare the URL to send the API
        String url = String.format(
                "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                locationName,
                APIKeys.GOOGLE_API_KEY
        );

        // Query the API
        HttpResponse<String> response = HttpRequests.sendGETRequest(url);

        // Deserialize the response
        JsonNode locationNode = GeocodeAPI.OBJECT_MAPPER.readTree(response.body()).path("results").get(0).path("geometry").path("location");
        return new Coordinates(locationNode.path("lat").asDouble(), locationNode.path("lng").asDouble());
    }
}
