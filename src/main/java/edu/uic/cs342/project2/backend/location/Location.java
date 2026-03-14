package edu.uic.cs342.project2.backend.location;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Coordinates coordinates;

    private String name, region, country;

    //private ZoneId zoneId;

    //private ZonedDateTime dateTime;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Location(Coordinates coordinates) throws NullPointerException {
        this.setCoordinates(coordinates);
        this.setName("");
        this.setRegion("");
        this.setCountry("");
    }

    public Location(Coordinates coordinates, String name, String region, String country) throws NullPointerException {
        this.setCoordinates(coordinates);
        this.setName(name);
        this.setRegion(region);
        this.setCountry(country);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if (coordinates == null) {
            throw new NullPointerException("coordinates is null");
        }
        this.coordinates = coordinates;
    }

    public void setName(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
    }

    public void setRegion(String region) throws NullPointerException {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        this.region = region;
    }

    public void setCountry(String country) throws NullPointerException {
        if (country == null) {
            throw new NullPointerException("country is null");
        }
        this.country = country;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Coordinates getCoordinates() { return this.coordinates; }

    public String getName() { return this.name; }

    public String getRegion() { return this.region; }

    public String getCountry() { return this.country; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        ArrayList<String> parts = new ArrayList<>();
        if (!this.name.isEmpty()) {
            parts.add(this.name);
        }

        if (!this.region.isEmpty()) {
            parts.add(this.region);
        }

        if (!this.country.isEmpty()) {
            parts.add(this.country);
        }

        return parts.isEmpty()
                ? this.coordinates.toString()
                : String.join(", ", parts);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        return this.coordinates.equals(other.coordinates) &&
                this.name.equals(other.name) &&
                this.region.equals(other.region) &&
                this.country.equals(other.country);
    }
}
