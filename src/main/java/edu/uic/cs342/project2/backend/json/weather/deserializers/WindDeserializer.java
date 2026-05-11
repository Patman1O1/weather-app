package edu.uic.cs342.project2.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.backend.json.weather.objects.Wind;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

import java.io.IOException;

public class WindDeserializer extends StdDeserializer<Wind> {
    public WindDeserializer() { super(Wind.class); }

    public Wind deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get the relevant nodes
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode directionNode = rootNode.get("direction");
        JsonNode speedNode = rootNode.get("speed");
        JsonNode gustNode = rootNode.get("gust");

        // Deserialize wind direction
        String cardinal = directionNode.path("cardinal").asText();
        Wind.Direction direction = Utilities.DIRECTIONS.get(cardinal);

        // Deserialize wind speed
        double speedValue = speedNode.path("value").asDouble();
        Wind.Speed speed = new Wind.Speed(speedValue, unitSystem);

        // Deserialize gust
        double gustValue = gustNode.path("value").asDouble();
        Wind.Gust gust = new Wind.Gust(gustValue, unitSystem);

        // Return a new instance of Wind with the deserialized information
        return new Wind(direction, speed, gust);
    }
}
