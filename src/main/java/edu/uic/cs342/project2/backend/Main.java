package edu.cs342.project2.backend;

import edu.cs342.project2.backend.api.*;
import edu.cs342.project2.backend.location.Coordinates;
import edu.cs342.project2.backend.location.DaylightInfo;
import edu.cs342.project2.backend.location.Location;
import edu.cs342.project2.backend.measurements.UnitSystem;
import edu.cs342.project2.backend.measurements.UnitSystems;
import edu.cs342.project2.backend.weather.CurrentWeather;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            Coordinates coordinates = GeolocationAPI.requestCoordinates();
            DaylightInfo daylightInfo = SunriseSunsetAPI.requestDaylightInfo(coordinates);
            System.out.println(coordinates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}