package org.patman101.weather_app.models.measurements;

public class UnitSystem {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private String name;

    private Unit temperature, speed, length, distance;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public UnitSystem(String name, Unit temperature, Unit length, Unit distance, Unit speed)
            throws NullPointerException {
        this.setName(name);
        this.setTemperature(temperature);
        this.setLength(length);
        this.setDistance(distance);
        this.setSpeed(speed);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setName(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        this.name = name;
    }

    public void setTemperature(Unit temperature) throws NullPointerException {
        if (temperature == null) {
            throw new NullPointerException("temperature is null");
        }
        this.temperature = temperature;
    }

    public void setLength(Unit length) throws NullPointerException {
        if (length == null) {
            throw new NullPointerException("length is null");
        }
        this.length = length;
    }

    public void setDistance(Unit distance) throws NullPointerException {
        if (distance == null) {
            throw new NullPointerException("distance is null");
        }
        this.distance = distance;
    }

    public void setSpeed(Unit speed) throws NullPointerException {
        if (speed == null) {
            throw new NullPointerException("speed is null");
        }
        this.speed = speed;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public String getName() { return this.name; }

    public Unit getTemperature() { return this.temperature; }

    public Unit getLength() { return this.length; }

    public Unit getDistance() { return this.distance; }

    public Unit getSpeed() { return this.speed; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return this.name; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UnitSystem other)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        return this.name.equals(other.name);
    }
}