package edu.uic.cs342.project2.frontend;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import edu.uic.cs342.project2.frontend.controllers.SettingsController;
import edu.uic.cs342.project2.frontend.ui.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.ConnectException;
import java.util.Objects;

public class WeatherApplication extends Application {
    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) { Application.launch(args); }

    private static void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            SettingsController.Settings settings = SettingsController.Settings.getInstance();
            if (settings.getTheme().equals(Themes.DARK)) {
                Application.setUserAgentStylesheet(
                        new PrimerDark().getUserAgentStylesheet());
            } else {
                Application.setUserAgentStylesheet(
                        new PrimerLight().getUserAgentStylesheet());
            }


            Parent root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/fxml/root.fxml")));

            Scene scene = new Scene(root, 800, 900);
            scene.getStylesheets().add(
                    Objects.requireNonNull(this.getClass().getResource("/styles/css/index.css")).toExternalForm()
            );

            primaryStage.setScene(scene);
            primaryStage.setTitle("Weather");
            primaryStage.show();
        } catch (ConnectException connectException) {
            WeatherApplication.showAlert(Alert.AlertType.ERROR, "Connection Error", "This app requires an internet connection");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}