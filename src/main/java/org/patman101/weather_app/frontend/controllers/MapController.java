package edu.uic.cs342.project2.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.util.Objects;

public class MapController {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private static final String MAP_URL = "/html/map.html";

    @FXML
    private WebView mapView;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public MapController() {}

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        this.mapView.getEngine().load(Objects.requireNonNull(this.getClass().getResource(MapController.MAP_URL)).toExternalForm());
    }
}
