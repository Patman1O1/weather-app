package edu.uic.cs342.project2.backend.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uic.cs342.project2.backend.weather.WeatherCondition;

public class WeatherConditionDeserializer extends StdDeserializer<WeatherCondition> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WeatherConditionDeserializer() { super(WeatherCondition.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public WeatherCondition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("JsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get relevant nodes
        JsonNode weatherConditionNode = jsonParser.getCodec().readTree(jsonParser);

        // Deserialize weather condition
        String type = weatherConditionNode.path("type").asText();
        String description = weatherConditionNode.path("description").path("text").asText();
        return new WeatherCondition(type, description);
    }
}
