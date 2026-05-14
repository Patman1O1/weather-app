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
import java.util.ArrayList;

public class HourlyForecastsDeserializer extends StdDeserializer<HourlyForecasts> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public HourlyForecastsDeserializer() { super(HourlyForecasts.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private static HourlyForecasts.Forecast deserializeForecast(DeserializationContext deserializationContext,
                                                                JsonParser jsonParser,
                                                                JsonNode forecastNode) throws IOException {
        // Deserialize datetime and daylight information
        Interval interval = deserializationContext.readTreeAsValue(forecastNode.get("interval"), Interval.class);
        boolean isDaytime = forecastNode.get("isDaytime").asBoolean();

        // Get the object mapper, create a copy of it, and inject the boolean value into the copy
        ObjectMapper objectMapper = ((ObjectMapper) jsonParser.getCodec())
                .copy()
                .setInjectableValues(new InjectableValues.Std().addValue(Boolean.class, isDaytime));


        // Deserialize temperature information
        WeatherCondition weatherCondition = objectMapper.treeToValue(forecastNode.path("weatherCondition"), WeatherCondition.class);
        Temperature temperature = deserializationContext.readTreeAsValue(forecastNode.path("temperature"), Temperature.class);
        Temperature feelsLikeTemperature = deserializationContext.readTreeAsValue(forecastNode.path("feelsLikeTemperature"), Temperature.class);
        Temperature dewPoint = deserializationContext.readTreeAsValue(forecastNode.path("dewPoint"), Temperature.class);
        Temperature heatIndex = deserializationContext.readTreeAsValue(forecastNode.path("heatIndex"), Temperature.class);
        Temperature windChill =  deserializationContext.readTreeAsValue(forecastNode.path("windChill"), Temperature.class);

        // Deserialize precipitation information
        Precipitation precipitation = deserializationContext.readTreeAsValue(forecastNode.path("precipitation"), Precipitation.class);
        double thunderstormProbability = forecastNode.get("thunderstormProbability").asDouble();
        double cloudCover = forecastNode.get("cloudCover").asDouble();

        // Deserialize other information
        double relativeHumidity = forecastNode.get("relativeHumidity").asDouble();
        double airPressure = forecastNode.path("airPressure").get("meanSeaLevelMillibars").asDouble();
        int uvIndex = forecastNode.get("uvIndex").asInt();
        Wind wind = deserializationContext.readTreeAsValue(forecastNode.path("wind"), Wind.class);
        Visibility visibility = deserializationContext.readTreeAsValue(forecastNode.path("visibility"), Visibility.class);

        return new HourlyForecasts.Forecast(
                interval,
                weatherCondition,
                temperature,
                feelsLikeTemperature,
                dewPoint,
                heatIndex,
                windChill,
                precipitation,
                wind,
                visibility,
                relativeHumidity,
                uvIndex,
                isDaytime,
                thunderstormProbability,
                airPressure,
                cloudCover
        );
    }

    @Override
    public HourlyForecasts deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the forecasts node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode forecastsNode = rootNode.path("forecastHours");

        ZoneId zoneId = ZoneId.of(rootNode.path("timeZone").get("id").asText());
        ArrayList<HourlyForecasts.Forecast> hourlyForecasts = new ArrayList<>(24);
        if (forecastsNode.isArray() && !forecastsNode.isEmpty()) {
            for (JsonNode forecastNode : forecastsNode) {
                hourlyForecasts.add(HourlyForecastsDeserializer.deserializeForecast(deserializationContext, jsonParser, forecastNode));
            }
        }
        return new HourlyForecasts(zoneId, hourlyForecasts);
    }

}
