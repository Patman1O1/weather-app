package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.Utilities;
import org.patman101.weather_app.backend.json.weather.deserializers.WindDeserializer;
import org.patman101.weather_app.frontend.ui.measurements.Measurement;
import org.patman101.weather_app.frontend.ui.measurements.Unit;
import org.patman101.weather_app.frontend.ui.measurements.UnitSystem;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WindDeserializer.class)
public class Wind {
    // ── Direction ────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Direction {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private String name, symbol;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Direction(String name, String symbol) throws NullPointerException {
            this.setName(name);
            this.setSymbol(symbol);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setName(String name) throws NullPointerException {
            if (name == null) {
                throw new NullPointerException("name is null");
            }
            this.name = name;
        }

        public void setSymbol(String symbol) throws NullPointerException {
            if (symbol == null) {
                throw new NullPointerException("symbol is null");
            }
            this.symbol = symbol;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public String getName() { return this.name; }

        public String getSymbol() { return this.symbol; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public String toString() { return this.name; }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Direction)) {
                return false;
            }

            if (this == object) {
                return true;
            }

            Direction other = (Direction) object;
            return this.name.equals(other.name) && this.symbol.equals(other.symbol);
        }
    }

    // ── Directions ───────────────────────────────────────────────────────────────────────────────────────────────────
    public static final class Directions {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        public static final Direction NORTH = new Direction("North", "N");

        public static final Direction NORTH_NORTHEAST = new Direction("North Northeast", "NNE");

        public static final Direction NORTHEAST = new Direction("Northeast", "NE");

        public static final Direction EAST_NORTHEAST = new Direction("East Northeast", "ENE");

        public static final Direction EAST = new Direction("East", "E");

        public static final Direction EAST_SOUTHEAST = new Direction("East Southeast", "ESE");

        public static final Direction SOUTHEAST = new Direction("Southeast", "SE");

        public static final Direction SOUTH_SOUTHEAST = new Direction("South Southeast", "SSE");

        public static final Direction SOUTH = new Direction("South", "S");

        public static final Direction SOUTH_SOUTHWEST = new Direction("South Southwest", "SSW");

        public static final Direction SOUTHWEST = new Direction("Southwest", "SW");

        public static final Direction WEST_SOUTHWEST = new Direction("West Southwest", "WSW");

        public static final Direction WEST = new Direction("West", "W");

        public static final Direction WEST_NORTHWEST = new Direction("West Northwest", "WNW");

        public static final Direction NORTHWEST = new Direction("Northwest", "NW");

        public static final Direction NORTH_NORTHWEST = new Direction("North Northwest", "NNW");

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        private Directions() {}
    }

    // ── Speed ────────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Speed extends Measurement {
        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Speed(Number number, UnitSystem unitSystem) throws NullPointerException {
            super(number, unitSystem.getSpeed());
        }
    }

    // ── Gust ─────────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Gust extends Measurement {
        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Gust(Number number, UnitSystem unitSystem) throws NullPointerException {
            super(number, unitSystem.getSpeed());
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Direction direction;

    private Speed speed;

    private Gust gust;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Wind(Direction direction, Speed speed, Gust gust) throws NullPointerException {
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

    public void setSpeed(Speed speed) throws NullPointerException {
        if (speed == null) {
            throw new NullPointerException("speed is null");
        }
        this.speed = speed;
    }

    public void setGust(Gust gust) throws NullPointerException {
        if (gust == null) {
            throw new NullPointerException("gust is null");
        }
        this.gust = gust;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Direction getDirection() { return this.direction; }

    public Speed getSpeed() { return this.speed; }

    public Gust getGust() { return this.gust; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Wind)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Wind other = (Wind) object;
        return this.direction.equals(other.direction) && this.speed.equals(other.speed) && this.gust.equals(other.gust);
    }
}
