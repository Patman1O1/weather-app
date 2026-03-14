package edu.uic.cs342.project2.backend.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.uic.cs342.project2.backend.measurements.Direction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Direction latitude, longitude;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Coordinates(double latitude, double longitude) throws NullPointerException {
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private void setLatitude(double latitude) throws IllegalArgumentException {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("latitude must be between -90 and 90 (inclusive)");
        }
        this.latitude = new Direction(latitude);
    }

    private void setLongitude(double longitude) throws IllegalArgumentException {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("longitude must be between -180 and 180 (inclusive)");
        }
        this.longitude = new Direction(longitude);
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Direction getLatitude() { return this.latitude; }

    public Direction getLongitude() { return this.longitude; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return String.format("(%s, %s)", this.latitude, this.longitude); }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates) object;
        return this.latitude.equals(other.latitude) && this.longitude.equals(other.longitude);
    }
}
