package edu.uic.cs342.project2.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.uic.cs342.project2.backend.json.weather.objects.Precipitation;
import edu.uic.cs342.project2.frontend.ui.measurements.UnitSystem;

import java.io.IOException;

public class PrecipitationDeserializer extends StdDeserializer<Precipitation> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public PrecipitationDeserializer() { super(Precipitation.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Precipitation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the relevant nodes
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode probabilityNode = rootNode.path("probability");
        JsonNode qpfNode = rootNode.path("qpf");

        // Get the unit system
        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Deserialize the probability node
        double percent = probabilityNode.path("percent").asDouble();
        String type = probabilityNode.path("type").asText();
        Precipitation.Probability probability = new Precipitation.Probability(percent, type);

        // Deserialize the QPF node
        double quantity = qpfNode.path("quantity").asDouble();
        Precipitation.Qpf qpf = new Precipitation.Qpf(quantity, unitSystem.getLength());

        // Return a new instance of Precipitation with the deserialized information
        return new Precipitation(probability, qpf);
    }
}
