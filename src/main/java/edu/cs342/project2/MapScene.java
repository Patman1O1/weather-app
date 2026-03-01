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

import java.util.Objects;

public class GoogleMapScene extends Scene {
    /* ----------------------------------------------------Fields---------------------------------------------------- */
    private static final String HTML_FILE = "/html/map.html";

    private WebView map;

    private HBox coordinates;

    private TextField latitudeField, longitudeField;

    private double latitude, longitude;

    /* -------------------------------------------------Constructors------------------------------------------------- */
    public GoogleMapScene() {
        super(new BorderPane(), 400.0, 300.0);
        this.longitude = this.latitude = 0.0;
        //this.setCoordinates();
        this.setMap();

        // resize map when window is resized
        this.widthProperty().addListener((obs, oldVal, newVal) -> {
            this.map.setPrefWidth(newVal.doubleValue());
        });
        this.heightProperty().addListener((obs, oldVal, newVal) -> {
            this.map.setPrefHeight(newVal.doubleValue());
        });
    }

    /* ---------------------------------------------------Setters---------------------------------------------------- */
    private void setMap() {
        this.map = new WebView();
        WebEngine webEngine = this.map.getEngine();
        webEngine.setJavaScriptEnabled(true); // Ensure JavaScript is enabled

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("weatherApp", this); // exposes bridge as window.javaApp
                Platform.runLater(() -> {
                    webEngine.executeScript(
                            "setTimeout(function() {" +
                                    "   google.maps.event.trigger(map, 'resize');" +
                                    "   map.setCenter({ lat: 40.7128, lng: -74.0060 });" +
                                    "}, 300);"
                    );
                });
            }
        });

        webEngine.load(Objects.requireNonNull(getClass().getResource(HTML_FILE)).toExternalForm());

        ((BorderPane) this.getRoot()).setCenter(this.map);
    }

    private void setCoordinates() {
        this.latitudeField = new TextField();
        this.latitudeField.setEditable(false);
        this.longitudeField = new TextField();
        this.longitudeField.setEditable(false);
        this.coordinates = new HBox(this.latitudeField, this.longitudeField);

        ((BorderPane) this.getRoot()).setTop(this.coordinates);
    }

    /* ---------------------------------------------------Getters---------------------------------------------------- */
    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    /* ---------------------------------------------------Methods---------------------------------------------------- */
    private void updateCoordinates(double latitude, double longitude) {
        Platform.runLater(() -> {
            this.latitude = latitude; this.longitude = longitude;
            //this.latitudeField.setText(Double.toString(latitude));
            //this.longitudeField.setText(Double.toString(longitude));
        });
    }

    public void onMapClick(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

    public void onMapMove(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

    public void onMapIdle(double latitude, double longitude) { this.updateCoordinates(latitude, longitude); }

}