package edu.uic.cs342.project2.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.uic.cs342.project2.backend.json.weather.objects.Visibility;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

import java.io.IOException;

public class VisibilityDeserializer extends StdDeserializer<Visibility> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public VisibilityDeserializer() { super(Visibility.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Visibility deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the unit system
        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get relevant nodes
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        // Deserialize
        double distance = rootNode.path("distance").asDouble();

        // Return a new instance of Visibility using the deserialized information
        return new Visibility(distance, unitSystem);
    }


}
