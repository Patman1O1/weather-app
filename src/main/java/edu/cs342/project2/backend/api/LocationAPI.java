package edu.cs342.project2.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationAPI {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final String KEY = "AIzaSyAXOFEgdWxFrqUpztN3vBl1gI-T1mpXVGk";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static ArrayList<Double> geolocationRequest() throws IOException, InterruptedException {
        String url = "https://www.googleapis.com/geolocation/v1/geolocate?key=" + LocationAPI.KEY;
        // Send POST request (Google Geolocation requires POST)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}")) // empty body is valid
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode locationNode = LocationAPI.OBJECT_MAPPER.readTree(response.body()).path("location");

        ArrayList<Double> coordinates = new ArrayList<>();
        coordinates.add(locationNode.hasNonNull("lat") ? locationNode.path("lat").asDouble() : 0.0);
        coordinates.add(locationNode.hasNonNull("lng") ? locationNode.path("lng").asDouble() : 0.0);

        return coordinates;
    }

    public static Map<String, String> geocodeRequest(double latitude, double longitude)
            throws RuntimeException, IOException, InterruptedException {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&key=" + LocationAPI.KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode rootNode = LocationAPI.OBJECT_MAPPER.readTree(response.body());

        // Check if the request was successfully fulfilled
        String status = rootNode.path("status").asText();
        if (!status.equals("OK")) {
            throw new RuntimeException("Geocoding API error: " + status);
        }

        Map<String, String> locationInfo = new HashMap<>();
        for (JsonNode componentNode : rootNode.path("results").get(0).path("address_components")) {
            String name = componentNode.path("long_name").asText();
            JsonNode types = componentNode.path("types");

            for (JsonNode type : types) {
                String typeText = type.asText();
                switch (typeText) {
                    case "locality":
                        locationInfo.put("name", name);
                        break;
                    case "administrative_area_level_1":
                        locationInfo.put("region", name);
                        break;
                    case "country":
                        locationInfo.put("country", name);
                        break;
                }
            }
        }
        return locationInfo;
    }

}
