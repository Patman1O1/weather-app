package org.patman101.weather_app.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.patman101.weather_app.backend.Utilities;
import org.patman101.weather_app.backend.json.weather.objects.WeatherCondition;

import java.io.IOException;

public class WeatherConditionDeserializer extends StdDeserializer<WeatherCondition> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WeatherConditionDeserializer() { super(WeatherCondition.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public WeatherCondition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the weatherCondition node
        JsonNode weatherConditionNode = jsonParser.getCodec().readTree(jsonParser);

        // Decipher the daylight condition
        boolean isDaytime = (boolean) deserializationContext.findInjectableValue(
                Boolean.class.getName(),
                null,
                null);

        // Deserialize relevant information
        String type = weatherConditionNode.path("type").asText();
        String iconPath = isDaytime
                ? String.format("/styles/icons/conditions/%s", Utilities.DAY_CONDITION_ICON_PATHS.get(type))
                : String.format("/styles/icons/conditions/%s", Utilities.NIGHT_CONDITION_ICON_PATHS.get(type));
        String description = weatherConditionNode.path("description").path("text").asText();

        // Create a new instance of WeatherCondition with the deserialize information
        return new WeatherCondition(type, description, iconPath);
    }
}
