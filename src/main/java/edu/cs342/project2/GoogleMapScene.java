package edu.cs342.project2;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class GoogleMapScene extends Scene {
    /* ----------------------------------------------------Fields---------------------------------------------------- */
    private static final String HTML_FILE = "/html/map.html";

    private WebView map;

    private double latitude, longitude;

    /* -------------------------------------------------Constructors------------------------------------------------- */
    public GoogleMapScene() {
        super(new BorderPane(), 400.0, 300.0);
        this.longitude = this.latitude = 0.0;
        this.setMap();
    }

    /* ---------------------------------------------------Setters---------------------------------------------------- */
    private void setMap() {
        this.map = new WebView();
        WebEngine webEngine = this.map.getEngine();
        webEngine.setJavaScriptEnabled(true); // Ensure JavaScript is enabled

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Load worker succeeded");
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("weatherApp", this); // exposes bridge as window.javaApp
            } else if (newState == Worker.State.FAILED) {
                System.out.println("Load worker failed");
            }
        });

        webEngine.load(getClass().getResource(HTML_FILE).toExternalForm());

        ((BorderPane) this.getRoot()).setCenter(this.map);
    }

    /* ---------------------------------------------------Getters---------------------------------------------------- */
    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    /* ---------------------------------------------------Methods---------------------------------------------------- */
    private void updateCoordinates(double latitude, double longitude) {
        Platform.runLater(() -> { this.latitude = latitude; this.longitude = longitude; });
    }

    public void onMapClick(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

    public void onMapMove(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

    public void onMapIdle(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

}