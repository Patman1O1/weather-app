package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.patman101.weather_app.backend.json.weather.deserializers.VisibilityDeserializer;
import org.patman101.weather_app.frontend.ui.measurements.Measurement;
import org.patman101.weather_app.frontend.ui.measurements.UnitSystem;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = VisibilityDeserializer.class)
public class Visibility extends Measurement {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Visibility(Number number, UnitSystem unitSystem) throws NullPointerException {
        super(number, unitSystem.getDistance());
    }
}
