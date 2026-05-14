package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.Utilities;
import org.patman101.weather_app.backend.json.weather.deserializers.TemperatureDeserializer;
import org.patman101.weather_app.frontend.ui.measurements.Measurement;
import org.patman101.weather_app.frontend.ui.measurements.UnitSystem;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = TemperatureDeserializer.class)
public class Temperature extends Measurement {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Temperature(Number number, UnitSystem unitSystem) throws NullPointerException {
        super(number, unitSystem.getTemperature());
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format("%d%s", Math.round(super.getNumber().doubleValue()), super.getUnit());
    }
}
