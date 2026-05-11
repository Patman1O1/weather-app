package edu.uic.cs342.project2.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.backend.json.weather.deserializers.TemperatureDeserializer;
import edu.uic.cs342.project2.frontend.ui.measurements.Measurement;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

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
