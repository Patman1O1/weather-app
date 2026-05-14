package org.patman101.weather_app.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.patman101.weather_app.backend.json.weather.objects.Interval;

import java.io.IOException;
import java.time.ZonedDateTime;

public class IntervalDeserializer extends StdDeserializer<Interval> {
    public IntervalDeserializer() { super(Interval.class); }

    @Override
    public Interval deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        return new Interval(ZonedDateTime.parse(rootNode.path("startTime").asText()),
                            ZonedDateTime.parse(rootNode.path("endTime").asText()));
    }

}
