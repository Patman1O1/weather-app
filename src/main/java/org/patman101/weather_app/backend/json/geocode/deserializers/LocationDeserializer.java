package org.patman101.weather_app.backend.json.geocode.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.patman101.weather_app.backend.json.geocode.objects.AddressComponents;
import org.patman101.weather_app.backend.json.geocode.objects.Location;

import java.io.IOException;

public class LocationDeserializer extends StdDeserializer<Location> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public LocationDeserializer() { super(Location.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Location deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the root node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        if (rootNode.path("status").isNull() || !rootNode.path("status").asText().equals("OK")) {
            throw new RuntimeException("Invalid request");
        }

        // Get the results node
        JsonNode resultsNode = rootNode.path("results").get(0);

        // Deserialize the latitude and longitude
        JsonNode locationNode = resultsNode.path("geometry").path("location");
        double latitude = locationNode.path("lat").asDouble();
        double longitude = locationNode.path("lng").asDouble();

        // Deserialize the formatted address
        String formattedAddress = resultsNode.path("formatted_address").asText();

        // Deserialize the address components
        AddressComponents addressComponents = deserializationContext.readTreeAsValue(rootNode, AddressComponents.class);

        // Return a new instance of Results with the deserialized information
        return new Location(formattedAddress, addressComponents, latitude, longitude);
    }
}
