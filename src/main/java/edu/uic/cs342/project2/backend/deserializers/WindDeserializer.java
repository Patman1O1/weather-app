package edu.uic.cs342.project2.backend.deserializers;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import edu.uic.cs342.project2.backend.measurements.Measurement;
import edu.uic.cs342.project2.backend.measurements.Direction;
import edu.uic.cs342.project2.backend.measurements.Unit;
import edu.uic.cs342.project2.backend.measurements.UnitSystem;
import edu.uic.cs342.project2.backend.weather.Wind;

import java.io.IOException;

public class WindDeserializer extends StdDeserializer<Wind> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WindDeserializer() { super(Wind.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Wind deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the unit system
        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get units
        Unit speedUnit = unitSystem.getSpeedUnit();
        Unit lengthUnit = unitSystem.getLengthUnit();

        // Get relevant nodes
        JsonNode windNode = (JsonNode) jsonParser.getCodec().readTree(jsonParser).path("wind");

        // Deserialize wind
        Direction windDirection = new Direction(windNode.path("direction").path("degrees").asDouble());
        Measurement windSpeed = new Measurement(windNode.path("speed").path("value").asDouble(), speedUnit);
        Measurement windGust = new Measurement(windNode.path("gust").path("value").asDouble(), speedUnit);
        return new Wind(windDirection, windSpeed, windGust);
    }
}
