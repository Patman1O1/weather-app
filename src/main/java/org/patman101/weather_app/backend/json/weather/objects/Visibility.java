package edu.uic.cs342.project2.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.uic.cs342.project2.backend.json.weather.deserializers.VisibilityDeserializer;
import edu.uic.cs342.project2.frontend.ui.measurements.Measurement;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = VisibilityDeserializer.class)
public class Visibility extends Measurement {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Visibility(Number number, UnitSystem unitSystem) throws NullPointerException {
        super(number, unitSystem.getDistance());
    }
}
