package edu.uic.cs342.project2.backend.json.weather.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.uic.cs342.project2.backend.json.weather.objects.*;

import java.io.IOException;
import java.time.ZoneId;

public class CurrentConditionsDeserializer extends StdDeserializer<CurrentConditions> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public CurrentConditionsDeserializer() { super(CurrentConditions.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public CurrentConditions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get relevant nodes
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode currentConditionsHistoryNode = rootNode.get("currentConditionsHistory");

        // Deserialize timezone info
        ZoneId timeZoneId = ZoneId.of(rootNode.path("timeZone").get("id").asText());
        boolean isDaytime = rootNode.path("isDaytime").asBoolean();

        // Get the object mapper, create a copy of it, and inject the boolean value into the copy
        ObjectMapper objectMapper = ((ObjectMapper) jsonParser.getCodec())
                .copy()
                .setInjectableValues(new InjectableValues.Std().addValue(Boolean.class, isDaytime));

        // Deserialize current conditions
        WeatherCondition condition = objectMapper.treeToValue(rootNode.path("weatherCondition"), WeatherCondition.class);

        // Deserialize temperature info
        Temperature temperature = deserializationContext.readTreeAsValue(rootNode.path("temperature"), Temperature.class);
        Temperature feelsLikeTemperature = deserializationContext.readTreeAsValue(rootNode.path("feelsLikeTemperature"), Temperature.class);
        Temperature dewPoint = deserializationContext.readTreeAsValue(rootNode.path("dewPoint"), Temperature.class);
        Temperature heatIndex = deserializationContext.readTreeAsValue(rootNode.path("heatIndex"), Temperature.class);
        Temperature windChill = deserializationContext.readTreeAsValue(rootNode.path("windChill"), Temperature.class);

        // Deserialize precipitation info
        Precipitation precipitation = deserializationContext.readTreeAsValue(rootNode.path("precipitation"), Precipitation.class);
        double thunderstormProbability = rootNode.get("thunderstormProbability").asDouble();
        double cloudCover = rootNode.get("cloudCover").asDouble();

        // Deserialize air and wind info
        double relativeHumidity = rootNode.get("relativeHumidity").asDouble();
        int uvIndex = rootNode.get("uvIndex").asInt();
        double airPressure = rootNode.path("airPressure").get("meanSeaLevelMillibars").asDouble();
        Visibility visibility = deserializationContext.readTreeAsValue(rootNode.path("visibility"), Visibility.class);
        Wind wind = deserializationContext.readTreeAsValue(rootNode.path("wind"), Wind.class);

        // Deserialize history
        Temperature temperatureChange = deserializationContext.readTreeAsValue(currentConditionsHistoryNode.path("temperatureChange"), Temperature.class);
        Temperature maxTemperature = deserializationContext.readTreeAsValue(currentConditionsHistoryNode.path("maxTemperature"), Temperature.class);
        Temperature minTemperature = deserializationContext.readTreeAsValue(currentConditionsHistoryNode.path("minTemperature"), Temperature.class);
        CurrentConditions.History history = new CurrentConditions.History(temperatureChange, maxTemperature, minTemperature);

        return new CurrentConditions(
                timeZoneId,
                condition,
                temperature,
                feelsLikeTemperature,
                dewPoint,
                heatIndex,
                windChill,
                precipitation,
                wind,
                visibility,
                history,
                isDaytime,
                relativeHumidity,
                uvIndex,
                thunderstormProbability,
                cloudCover,
                airPressure
        );
    }
}
