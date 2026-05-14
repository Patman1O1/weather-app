package org.patman101.weather_app.backend.json.geocode.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.patman101.weather_app.backend.json.geocode.objects.AddressComponents;

import java.io.IOException;
import java.util.ArrayList;

public class AddressComponentsDeserializer extends StdDeserializer<AddressComponents> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public AddressComponentsDeserializer() { super(AddressComponents.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public AddressComponents deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if  (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the results node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode resultsNode = rootNode.path("results");

        // Create an array list to store the deserialized address components
        ArrayList<AddressComponents.AddressComponent> addressComponents = new ArrayList<>();

        // Ensure the node is a non-empty JSON array
        if (resultsNode.isArray() && !resultsNode.isEmpty()) {

            // Get the node containing the address components
            JsonNode addressComponentsNode = resultsNode.get(0).path("address_components");

            if (addressComponentsNode.isArray()) {

                for (JsonNode addressComponentNode : addressComponentsNode) {

                    String longName = addressComponentNode.path("long_name").asText();
                    String shortName = addressComponentNode.path("short_name").asText();

                    // types is an array
                    ArrayList<String> types = new ArrayList<>();
                    JsonNode typesNode = addressComponentNode.path("types");

                    if (typesNode.isArray()) {
                        for (JsonNode typeNode : typesNode) {
                            types.add(typeNode.asText());
                        }
                    }

                    // Create a new AddressComponent instance to store the deserialized information
                    AddressComponents.AddressComponent component =
                            new AddressComponents.AddressComponent(
                                    longName,
                                    shortName,
                                    types
                            );

                    // Add the instance to the array list
                    addressComponents.add(component);
                }
            }
        }

        // Return a new instance of AddressComponents containing the deserialized address components
        return new AddressComponents(addressComponents);
    }
}
