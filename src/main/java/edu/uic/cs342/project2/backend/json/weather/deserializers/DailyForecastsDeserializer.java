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
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class DailyForecastsDeserializer extends StdDeserializer<DailyForecasts> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public DailyForecastsDeserializer() { super(DailyForecasts.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    private static DailyForecasts.Forecast deserializeForecast(DeserializationContext deserializationContext,
                                                               JsonParser jsonParser,
                                                               JsonNode forecastNode,
                                                               boolean isDaytime) throws IOException {
        // Deserialize datetime information
        Interval interval = deserializationContext.readTreeAsValue(forecastNode.path("interval"), Interval.class);

        // Deserialize precipitation information
        Precipitation precipitation = deserializationContext.readTreeAsValue(forecastNode.path("precipitation"), Precipitation.class);
        double thunderstormProbability = forecastNode.get("thunderstormProbability").asDouble();
        Wind wind =  deserializationContext.readTreeAsValue(forecastNode.path("wind"), Wind.class);
        double cloudCover = forecastNode.get("cloudCover").asDouble();

        // Get the object mapper, create a copy of it, and inject the boolean value into the copy
        ObjectMapper objectMapper = ((ObjectMapper) jsonParser.getCodec())
                .copy()
                .setInjectableValues(new InjectableValues.Std().addValue(Boolean.class, isDaytime));


        // Deserialize other information
        WeatherCondition weatherCondition = objectMapper.treeToValue(forecastNode.path("weatherCondition"), WeatherCondition.class);
        double relativeHumidity = forecastNode.get("relativeHumidity").asDouble();
        int uvIndex = forecastNode.get("uvIndex").asInt();

        return new DailyForecasts.Forecast(
                interval,
                weatherCondition,
                precipitation,
                wind,
                relativeHumidity,
                uvIndex,
                thunderstormProbability,
                cloudCover
        );
    }

    private static DailyForecasts.DailyForecast deserializeDailyForecast(DeserializationContext deserializationContext,
                                                                         JsonParser jsonParser,
                                                                         JsonNode dailyForecastNode) throws IOException {
        // Deserialize daytime forecast
        DailyForecasts.Forecast daytimeForecast = DailyForecastsDeserializer
                .deserializeForecast(deserializationContext,
                                     jsonParser,
                                     dailyForecastNode.path("daytimeForecast"),
                            true);

        // Deserialize nighttime forecast
        DailyForecasts.Forecast nighttimeForecast = DailyForecastsDeserializer
                .deserializeForecast(deserializationContext,
                                     jsonParser,
                                     dailyForecastNode.path("nighttimeForecast"),
                            false);

        Temperature maxTemperature = deserializationContext
                .readTreeAsValue(dailyForecastNode.path("maxTemperature"),
                                 Temperature.class);

        Temperature minTemperature = deserializationContext
                .readTreeAsValue(dailyForecastNode.path("minTemperature"),
                                 Temperature.class);

        Temperature feelsLikeMaxTemperature = deserializationContext
                .readTreeAsValue(dailyForecastNode.path("feelsLikeMaxTemperature"),
                                 Temperature.class);

        Temperature feelsLikeMinTemperature = deserializationContext
                .readTreeAsValue(dailyForecastNode.path("feelsLikeMinTemperature"),
                                 Temperature.class);

        JsonNode sunEventsNode = dailyForecastNode.path("sunEvents");
        ZonedDateTime sunriseTime = ZonedDateTime.parse(sunEventsNode.path("sunriseTime").asText());
        ZonedDateTime sunsetTime = ZonedDateTime.parse(sunEventsNode.path("sunsetTime").asText());

        return new DailyForecasts.DailyForecast(daytimeForecast,
                                                nighttimeForecast,
                                                maxTemperature,
                                                minTemperature,
                                                feelsLikeMaxTemperature,
                                                feelsLikeMinTemperature,
                                                sunriseTime,
                                                sunsetTime);
    }

    @Override
    public DailyForecasts deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("jsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the daily forecasts node
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode dailyForecastsNode = rootNode.path("forecastDays");

        // Deserialize each daily forecast
        ZoneId zonedId = ZoneId.of(rootNode.path("timeZone").get("id").asText());
        ArrayList<DailyForecasts.DailyForecast> dailyForecasts = new ArrayList<>(7);
        if (dailyForecastsNode.isArray() && !dailyForecastsNode.isEmpty()) {
            for (JsonNode dailyForecastNode : dailyForecastsNode) {
                dailyForecasts.add(DailyForecastsDeserializer.deserializeDailyForecast(deserializationContext,
                                                                                       jsonParser,
                                                                                       dailyForecastNode));
            }
        }
        return new DailyForecasts(zonedId, dailyForecasts);
    }
}
