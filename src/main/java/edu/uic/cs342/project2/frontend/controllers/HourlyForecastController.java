package edu.uic.cs342.project2.frontend.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HourlyForecastController implements Initializable {
    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException {
        if (url == null) {
            throw new NullPointerException("url is null");
        }

        if (resourceBundle == null) {
            throw new NullPointerException("resourceBundle is null");
        }
    }
}
