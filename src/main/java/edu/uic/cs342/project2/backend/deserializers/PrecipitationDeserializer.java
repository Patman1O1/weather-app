package edu.uic.cs342.project2.backend.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uic.cs342.project2.backend.measurements.Measurement;
import edu.uic.cs342.project2.backend.measurements.Percent;
import edu.uic.cs342.project2.backend.measurements.Unit;
import edu.uic.cs342.project2.backend.measurements.UnitSystem;
import edu.uic.cs342.project2.backend.weather.Precipitation;

public class PrecipitationDeserializer extends StdDeserializer<Precipitation> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public PrecipitationDeserializer() { super(Precipitation.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Precipitation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("JsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the unit system
        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get units
        Unit lengthUnit = unitSystem.getLengthUnit();

        // Get relevant nodes
        JsonNode precipitationNode = (JsonNode) jsonParser.getCodec().readTree(jsonParser).path("precipitation");

        // Deserialize precipitation
        String precipitationType = precipitationNode.path("probability").path("type").asText();
        Percent precipitationProbability = new Percent(precipitationNode.path("probability").path("percent").asDouble());
        Measurement precipitationQpf = new Measurement(precipitationNode.path("qpf").path("quantity").doubleValue(), lengthUnit);
        return new Precipitation(precipitationType, precipitationProbability, precipitationQpf);
    }

}
