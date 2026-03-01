package edu.cs342.project2;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.*;

public class Location {
    /* ---------------------------------------------------Fields----------------------------------------------------- */
    private static final String GOOGLE_API_KEY = "AIzaSyAXOFEgdWxFrqUpztN3vBl1gI-T1mpXVGk";

    private double latitude, longitude, accuracy;

    /* ------------------------------------------------Constructors-------------------------------------------------- */
    public Location() throws IOException, InterruptedException { this.update(); }

    /* --------------------------------------------------Getters----------------------------------------------------- */
    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public double getAccuracy() { return accuracy; }

    /* --------------------------------------------------Methods----------------------------------------------------- */
    public void update() throws IOException, InterruptedException {
        String url = "https://www.googleapis.com/geolocation/v1/geolocate?key=" + GOOGLE_API_KEY;

        HttpClient client = HttpClient.newHttpClient();

        // The Geolocation API requires a POST request with a JSON body
        // An empty body still works and returns a location based on your IP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        JSONObject jsonObject = new JSONObject(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
        JSONObject location = jsonObject.getJSONObject("location");
        this.latitude = location.getDouble("lat");
        this.longitude = location.getDouble("lng");
        this.accuracy = jsonObject.getDouble("accuracy");
    }

    @Override
    public String toString() { return "Latitude: " + this.latitude + ", Longitude: " + this.longitude; }
}