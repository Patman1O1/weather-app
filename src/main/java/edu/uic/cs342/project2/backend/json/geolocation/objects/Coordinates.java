package edu.uic.cs342.project2.backend.json.geolocation.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.backend.json.geolocation.deserializers.CoordinatesDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CoordinatesDeserializer.class)
public class Coordinates {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    public double latitude, longitude;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Coordinates() { this.latitude = 0.0; this.longitude = 0.0; }

    public Coordinates(double latitude, double longitude) { this.latitude = latitude; this.longitude = longitude; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() { return String.format("(%.2f, %.2f)", this.latitude, this.longitude); }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinates)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        Coordinates other = (Coordinates) object;
        return Utilities.doubleEquals(this.latitude, other.latitude) &&
                Utilities.doubleEquals(this.longitude, other.longitude);
    }
}
