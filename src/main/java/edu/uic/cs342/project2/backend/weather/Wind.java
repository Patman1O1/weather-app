package edu.uic.cs342.project2.backend.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.uic.cs342.project2.backend.deserializers.WindDeserializer;
import edu.uic.cs342.project2.backend.measurements.Direction;
import edu.uic.cs342.project2.backend.measurements.Measurement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WindDeserializer.class)
public class Wind {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Direction direction;

    private Measurement speed, gust;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Wind(Direction direction, Measurement speed, Measurement gust) throws NullPointerException {
        this.setDirection(direction);
        this.setSpeed(speed);
        this.setGust(gust);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setDirection(Direction direction) throws NullPointerException {
        if (direction == null) {
            throw new NullPointerException("direction is null");
        }
        this.direction = direction;
    }

    public void setSpeed(Measurement speed) throws NullPointerException {
        if (speed == null) {
            throw new NullPointerException("speed is null");
        }
        this.speed = speed;
    }

    public void setGust(Measurement gust) throws NullPointerException {
        if (gust == null) {
            throw new NullPointerException("gust is null");
        }
        this.gust = gust;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Direction getDirection() { return this.direction; }

    public Measurement getSpeed() { return this.speed; }

    public Measurement getGust() { return this.gust; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
                "Wind Direction: %s\nWind Speed: %s\nWind Gust: %s",
                this.direction.cardinalValue(),
                this.speed,
                this.gust
        );
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Wind)) {
            return false;
        }
        Wind other = (Wind) object;
        return this.direction.equals(other.direction) && this.speed.equals(other.speed) && this.gust.equals(other.gust);
    }
}
