package edu.uic.cs342.project2.backend.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uic.cs342.project2.backend.measurements.Measurement;
import edu.uic.cs342.project2.backend.measurements.Percent;
import edu.uic.cs342.project2.backend.measurements.Unit;
import edu.uic.cs342.project2.backend.measurements.UnitSystem;
import edu.uic.cs342.project2.backend.weather.Precipitation;
import edu.uic.cs342.project2.backend.weather.Weather;
import edu.uic.cs342.project2.backend.weather.WeatherCondition;
import edu.uic.cs342.project2.backend.weather.Wind;

public class WeatherDeserializer extends StdDeserializer<Weather> {
    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public WeatherDeserializer() { super(Weather.class); }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public Weather deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, NullPointerException {
        if (jsonParser == null) {
            throw new NullPointerException("JsonParser is null");
        }

        if (deserializationContext == null) {
            throw new NullPointerException("deserializationContext is null");
        }

        // Get the unit system
        UnitSystem unitSystem = (UnitSystem) deserializationContext.findInjectableValue(UnitSystem.class.getName(), null, null);

        // Get units
        Unit tempUnit = unitSystem.getTempUnit();
        Unit pressureUnit = unitSystem.getPressureUnit();
        Unit lengthUnit = unitSystem.getLengthUnit();
        Unit distanceUnit = unitSystem.getDistanceUnit();

        // Get relevant nodes
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        // Prepare injectable values
        InjectableValues injectableValues = new InjectableValues.Std()
                .addValue(UnitSystem.class, unitSystem);

        // Create an object mapper and inject the values into it
        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        objectMapper.setInjectableValues(injectableValues);

        // Deserialize temperature measurements
        Measurement temperature = new Measurement(rootNode.path("temperature").path("degrees").asDouble(), tempUnit);
        Measurement feelsLikeTemperature = new Measurement(rootNode.path("feelsLikeTemperature").path("degrees").asDouble(), tempUnit);
        Measurement dewPoint = new Measurement(rootNode.path("dewPoint").path("degrees").asDouble(), tempUnit);
        Measurement heatIndex  = new Measurement(rootNode.path("heatIndex").path("degrees").asDouble(), tempUnit);
        Measurement windChill = new Measurement(rootNode.path("windChill").path("degrees").asDouble(), tempUnit);

        // Deserialize relative humidity
        Percent relativeHumidity =  new Percent(rootNode.path("relativeHumidity").asDouble());

        // Deserialize UV index
        int uvIndex = rootNode.path("uvIndex").asInt();

        // Deserialize precipitation
        Precipitation precipitation = objectMapper.treeToValue(rootNode.path("precipitation"), Precipitation.class);

        // Deserialize thunderstorm probability
        Percent thunderstormProbability = new Percent(rootNode.path("thunderstormProbability").asDouble());

        // Deserialize wind
        Wind wind = objectMapper.treeToValue(rootNode.path("wind"), Wind.class);

        // Deserialize visibility
        Measurement visibility = new Measurement(rootNode.path("visibility").path("distance").asDouble(), distanceUnit);

        // Deserialize cloud coverage
        Percent cloudCoverage = new Percent(rootNode.path("cloudCover").doubleValue());

        // Deserialize air pressure
        Measurement airPressure = new Measurement(rootNode.path("airPressure").path("meanSeaLevelMillibars").asDouble(), pressureUnit);

        // Deserialize weather condition
        WeatherCondition condition = objectMapper.treeToValue(rootNode.path("weatherCondition"), WeatherCondition.class);

        return new Weather(
                temperature,
                feelsLikeTemperature,
                dewPoint,
                heatIndex,
                windChill,
                visibility,
                airPressure,
                thunderstormProbability,
                cloudCoverage,
                relativeHumidity,
                uvIndex,
                wind,
                precipitation,
                condition
        );
    }
}
