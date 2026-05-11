package edu.uic.cs342.project2.backend;

import edu.uic.cs342.project2.backend.api.GeolocationAPI;
import edu.uic.cs342.project2.backend.api.WeatherAPI;
import edu.uic.cs342.project2.backend.json.geocode.objects.Location;
import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;
import edu.uic.cs342.project2.backend.json.weather.objects.DailyForecasts;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystems;

public class Main {
    public static void main(String[] args) {
        try {
            Coordinates coordinates = GeolocationAPI.requestCoordinates();
            DailyForecasts forecasts = WeatherAPI.requestDailyForecasts(coordinates.latitude, coordinates.longitude, UnitSystems.IMPERIAL);
            int x = 0;
            x++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}