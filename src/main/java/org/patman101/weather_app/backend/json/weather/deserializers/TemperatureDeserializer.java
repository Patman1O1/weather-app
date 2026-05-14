package org.patman101.weather_app.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.patman101.weather_app.backend.json.weather.objects.Temperature;
import org.patman101.weather_app.frontend.ui.measurements.UnitSystem;

import java.io.IOException;

public class TemperatureDeserializer extends StdDeserializer<Temperature> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public TemperatureDeserializer() { super(Temperature.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Temperature deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get the root node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        // Deserialize
        double degrees = rootNode.get("degrees").asDouble();


        // Create a new instance of Temperature with the deserialized information
        return new Temperature(degrees, unitSystem);
    }

}
