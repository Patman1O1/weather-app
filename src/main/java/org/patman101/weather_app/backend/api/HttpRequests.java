package org.patman101.weather_app.backend.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpRequests {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    private HttpRequests() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private static <T> HttpResponse<T> sendRequest(HttpRequest request, HttpResponse.BodyHandler<T> bodyHandler)
            throws IOException, InterruptedException, RuntimeException {
        // Send the request
        HttpResponse<T> response = HttpRequests.HTTP_CLIENT.send(request, bodyHandler);

        // Ensure a valid response was returned
        int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + statusCode);
        }

        return response;
    }

    public static <T> HttpResponse<T> sendGenericGETRequest(String url, HttpResponse.BodyHandler<T> bodyHandler)
            throws IOException, InterruptedException, RuntimeException {
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        if (bodyHandler == null) {
            throw new NullPointerException("bodyHandler is null");
        }

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return HttpRequests.sendRequest(request, bodyHandler);
    }

    public static HttpResponse<String> sendGETRequest(String url)
            throws IOException, InterruptedException, NullPointerException {
        return HttpRequests.sendGenericGETRequest(url, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendPOSTRequest(String url, String body)
            throws IOException, InterruptedException, RuntimeException {
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        if (body == null) {
            throw new NullPointerException("body is null");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return HttpRequests.sendRequest(request, HttpResponse.BodyHandlers.ofString());
    }
}
