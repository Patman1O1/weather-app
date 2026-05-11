package edu.uic.cs342.project2.frontend.ui;

import edu.uic.cs342.project2.backend.api.GeocodeAPI;
import edu.uic.cs342.project2.backend.api.GeolocationAPI;
import edu.uic.cs342.project2.backend.json.geocode.objects.Location;
import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;

import java.io.IOException;
import java.net.ConnectException;

public class UserLocation {
    private static UserLocation instance;

    private Coordinates coordinates;

    private Location location;

    private UserLocation() throws IOException, InterruptedException { this.update(); }

    public void update() throws IOException, InterruptedException {
        this.coordinates = GeolocationAPI.requestCoordinates();
        this.location = GeocodeAPI.requestLocation(this.coordinates.latitude, this.coordinates.longitude);
    }

    public void update(double latitude, double longitude) throws IOException, InterruptedException {
        this.coordinates = new Coordinates(latitude, longitude);
        this.location = GeocodeAPI.requestLocation(this.coordinates.latitude, this.coordinates.longitude);
    }

    public static UserLocation getInstance() throws IOException, InterruptedException {
        if (UserLocation.instance == null) {
            UserLocation.instance = new UserLocation();
        }
        return UserLocation.instance;
    }

    public Coordinates getCoordinates() { return this.coordinates; }

    public Location getLocation() { return this.location; }

}
