package edu.uic.cs342.project2.frontend.controllers;

import atlantafx.base.theme.Styles;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import edu.uic.cs342.project2.frontend.ui.UserLocation;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LocationController {
    // ── Fetch Suggestions ────────────────────────────────────────────────────────────────────────────────────────────
    private static class FetchSuggestions extends Task<List<JsonNode>> {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

        private final URI uri;

        public FetchSuggestions(String query) throws NullPointerException {
            if (query == null) {
                throw new NullPointerException("query is null");
            }

            this.uri = URI.create(String.format("https://nominatim.openstreetmap.org/search?format=json&limit=5&q=%s",
                    URLEncoder.encode(query, StandardCharsets.UTF_8)));
        }

        @Override
        public List<JsonNode> call() throws Exception {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(this.uri)
                    .header("User-Agent", "WeatherApp/1.0")
                    .build();
            HttpResponse<String> response = FetchSuggestions.HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode root = FetchSuggestions.OBJECT_MAPPER.readTree(response.body());
            List<JsonNode> results = new ArrayList<>();
            root.forEach(results::add);
            return results;
        }
    }
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private final UserLocation userLocation;

    private PauseTransition pause;

    private Popup dropdown;

    @FXML
    private Label currentLocationLabel;

    @FXML
    private TextField searchInput;

    @FXML
    private Label statusLabel;

    @FXML
    private HBox searchRow;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public LocationController() throws IOException, InterruptedException {
        this.userLocation = UserLocation.getInstance();
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private HBox buildRow(JsonNode node) throws RuntimeException {
        // Extract info
        String displayName = node.get("display_name").asText();
        double latitude = node.get("lat").asDouble();
        double longitude = node.get("lon").asDouble();

        // Split display name into city + rest for two-line display
        String[] parts = displayName.split(",", 2);
        String cityName = parts[0].trim();

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 16, 10, 16));

        VBox info = new VBox(2);
        Label cityLbl = new Label(cityName);
        cityLbl.getStyleClass().add(Styles.TEXT_BOLD);
        Label countryLbl = new Label(parts.length > 1 ? parts[1].trim() : "");
        countryLbl.getStyleClass().add(Styles.TEXT_MUTED);
        countryLbl.setStyle("-fx-font-size:11;");
        info.getChildren().addAll(cityLbl, countryLbl);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label addBadge = new Label("＋");
        addBadge.setStyle("-fx-font-size:16; -fx-text-fill:-color-accent-fg;");
        row.getChildren().addAll(info, addBadge);

        row.setStyle("-fx-cursor:hand;");
        row.setStyle("-fx-cursor:hand;");
        row.setOnMouseEntered(event -> row.setStyle("-fx-background-color:-color-bg-subtle; -fx-cursor:hand;"));
        row.setOnMouseExited(event -> row.setStyle("-fx-cursor:hand;"));
        row.setOnMouseClicked(event -> {
            if (this.dropdown != null) {
                this.dropdown.hide();
                this.dropdown = null;
            }
            try {
                this.userLocation.update(latitude, longitude);
            } catch (IOException e) {
                throw new RuntimeException("IOException thrown with message: " + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException("InterruptedException thrown with message: " + e.getMessage());
            }
            this.currentLocationLabel.setText(displayName);
            this.searchInput.setText(this.userLocation.getLocation().getFormattedAddress());
            showStatus("Location Updated");
        });

        return row;
    }

    private void showDropdown(List<JsonNode> results) throws RuntimeException {
        if (results.isEmpty()) {
            return;
        }

        VBox content = new VBox();
        content.setStyle(
                "-fx-background-color: -color-bg-default;" +
                        "-fx-border-color: -color-border-default;" +
                        "-fx-border-radius: 12; -fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.15),12,0,0,4);"
        );

        for (JsonNode node : results) {
            content.getChildren().add(this.buildRow(node));
        }

        Bounds b = this.searchRow.localToScreen(this.searchRow.getBoundsInLocal());
        if (b == null) {
            return;
        }
        content.setPrefWidth(b.getWidth());

        this.dropdown = new Popup();
        this.dropdown.getContent().add(content);
        this.dropdown.setAutoHide(true);
        this.dropdown.show(this.searchRow, b.getMinX(), b.getMaxY() + 4);
    }

    private void fetchSuggestions(String query) throws RuntimeException {
        Task<List<JsonNode>> task = new FetchSuggestions(query);

        task.setOnSucceeded(event -> this.showDropdown(task.getValue()));
        task.setOnFailed(event -> task.getException().printStackTrace());
        new Thread(task).start();
    }

    private void handleSearch(String query) throws RuntimeException {
        if (this.dropdown != null) {
            this.dropdown.hide();
            this.dropdown = null;
        }

        if (query == null || query.trim().isEmpty()) {
            return;
        }

        if (this.pause != null) {
            this.pause.stop();
        }
        this.pause = new PauseTransition(Duration.millis(350.0));
        this.pause.setOnFinished(event -> this.fetchSuggestions(query.trim()));
        this.pause.play();
    }

    private void showStatus(String message) {
        this.statusLabel.setText(message);
        this.statusLabel.setVisible(true);

        PauseTransition fade = new PauseTransition(Duration.seconds(2));
        fade.setOnFinished(event -> this.statusLabel.setVisible(false));
        fade.play();
    }

    @FXML
    public void initialize() throws RuntimeException {
        this.currentLocationLabel.setText(this.userLocation.getLocation().getFormattedAddress());
        this.searchInput.textProperty().addListener((observableValue, oldQuery, query) -> handleSearch(query));

        this.searchInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (this.userLocation.getLocation().getFormattedAddress() != null) {
                    this.showStatus("Location Updated");
                }
            }
        });
    }
}