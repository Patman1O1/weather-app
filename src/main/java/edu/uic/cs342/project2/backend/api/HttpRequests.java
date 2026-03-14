package edu.uic.cs342.project2.backend.api;

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
    private static HttpResponse<String> sendRequest(HttpRequest request)
            throws IOException, InterruptedException, RuntimeException {
        // Send the request
        HttpResponse<String> response = HttpRequests.HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        // Ensure a valid response was returned
        int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + statusCode);
        }

        return response;
    }

    public static HttpResponse<String> sendGETRequest(String url)
            throws IOException, InterruptedException, NullPointerException {
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        // Build the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request
        return HttpRequests.sendRequest(request);
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
        return HttpRequests.sendRequest(request);
    }
}
