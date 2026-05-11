package edu.uic.cs342.project2.backend.json.geolocation.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.uic.cs342.project2.backend.json.geolocation.objects.Coordinates;

import java.io.IOException;

public class CoordinatesDeserializer extends StdDeserializer<Coordinates> {
    public CoordinatesDeserializer() { super(Coordinates.class); }

    @Override
    public Coordinates deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the locations node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode locationNode = rootNode.get("location");

        // Return a new instance of Location with the deserialized information
        return new Coordinates(locationNode.path("lat").asDouble(), locationNode.path("lng").asDouble());
    }
}
