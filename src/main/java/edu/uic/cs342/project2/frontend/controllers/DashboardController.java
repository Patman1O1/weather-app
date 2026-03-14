package edu.cs342.project2.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    /* ---------------------------------------------------Fields----------------------------------------------------- */
    @FXML
    protected HBox sceneRoot, weatherLabelRoot, dashboardButtonRoot, locationButtonRoot, settingsButtonRoot;

    @FXML
    protected VBox dashboardRoot, menuRoot;

    /* ------------------------------------------------Constructors-------------------------------------------------- */

    /* --------------------------------------------------Methods----------------------------------------------------- */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sceneRoot = new HBox();
    }
}
