package edu.uic.cs342.project2.frontend.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class SevenDayForecastController implements Initializable {
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
