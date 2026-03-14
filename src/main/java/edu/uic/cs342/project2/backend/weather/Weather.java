package edu.uic.cs342.project2.backend.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.uic.cs342.project2.backend.deserializers.WeatherDeserializer;
import edu.uic.cs342.project2.backend.measurements.Measurement;
import edu.uic.cs342.project2.backend.measurements.Percent;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WeatherDeserializer.class)
public class Weather {
    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private Measurement temperature, feelsLikeTemperature, dewPoint, heatIndex, windChill, visibility, airPressure;

    private Percent thunderstormProbability, cloudCoverage, relativeHumidity;

    private int uvIndex;

    private Wind wind;

    private Precipitation precipitation;

    private WeatherCondition condition;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public Weather(Measurement temperature,
                   Measurement feelsLikeTemperature,
                   Measurement dewPoint,
                   Measurement heatIndex,
                   Measurement windChill,
                   Measurement visibility,
                   Measurement airPressure,
                   Percent thunderstormProbability,
                   Percent cloudCoverage,
                   Percent relativeHumidity,
                   int uvIndex,
                   Wind wind,
                   Precipitation precipitation,
                   WeatherCondition condition) throws NullPointerException {
        this.setTemperature(temperature);
        this.setFeelsLikeTemperature(feelsLikeTemperature);
        this.setDewPoint(dewPoint);
        this.setHeatIndex(heatIndex);
        this.setWindChill(windChill);
        this.setVisibility(visibility);
        this.setAirPressure(airPressure);
        this.setThunderstormProbability(thunderstormProbability);
        this.setCloudCoverage(cloudCoverage);
        this.setRelativeHumidity(relativeHumidity);
        this.setUvIndex(uvIndex);
        this.setWind(wind);
        this.setPrecipitation(precipitation);
        this.setCondition(condition);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setTemperature(Measurement temperature) throws NullPointerException {
        if (temperature == null) {
            throw new  NullPointerException("temperature is null");
        }
        this.temperature = temperature;
    }

    public void setFeelsLikeTemperature(Measurement feelsLikeTemperature) throws NullPointerException {
        if (feelsLikeTemperature == null) {
            throw new  NullPointerException("feelsLikeTemperature is null");
        }
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public void setDewPoint(Measurement dewPoint) throws NullPointerException {
        if (dewPoint == null) {
            throw new  NullPointerException("dewPoint is null");
        }
        this.dewPoint = dewPoint;
    }

    public void setHeatIndex(Measurement heatIndex) throws NullPointerException {
        if (heatIndex == null) {
            throw new  NullPointerException("heatIndex is null");
        }
        this.heatIndex = heatIndex;
    }

    public void setWindChill(Measurement windChill) throws NullPointerException {
        if (windChill == null) {
            throw new  NullPointerException("windChill is null");
        }
        this.windChill = windChill;
    }

    public void setVisibility(Measurement visibility) throws NullPointerException {
        if (visibility == null) {
            throw new  NullPointerException("visibility is null");
        }
        this.visibility = visibility;
    }

    public void setAirPressure(Measurement airPressure) throws NullPointerException {
        if (airPressure == null) {
            throw new  NullPointerException("airPressure is null");
        }
        this.airPressure = airPressure;
    }

    public void setThunderstormProbability(Percent thunderstormProbability) throws NullPointerException {
        if (thunderstormProbability == null) {
            throw new  NullPointerException("thunderstormProbability is null");
        }
        this.thunderstormProbability = thunderstormProbability;
    }

    public void setCloudCoverage(Percent cloudCoverage) throws NullPointerException {
        if (cloudCoverage == null) {
            throw new  NullPointerException("cloudCoverage is null");
        }
        this.cloudCoverage = cloudCoverage;
    }

    public void setRelativeHumidity(Percent relativeHumidity) throws NullPointerException {
        if (relativeHumidity == null) {
            throw new  NullPointerException("relativeHumidity is null");
        }
        this.relativeHumidity = relativeHumidity;
    }

    public void setUvIndex(int uvIndex) throws IllegalArgumentException {
        if (uvIndex < 0) {
            throw new IllegalArgumentException("uvIndex is negative");
        }
        this.uvIndex = uvIndex;
    }

    public void setWind(Wind wind) throws NullPointerException {
        if (wind == null) {
            throw new  NullPointerException("wind is null");
        }
        this.wind = wind;
    }

    public void setPrecipitation(Precipitation precipitation) throws NullPointerException {
        if (precipitation == null) {
            throw new  NullPointerException("precipitation is null");
        }
        this.precipitation = precipitation;
    }

    public void setCondition(WeatherCondition condition) throws NullPointerException {
        if (condition == null) {
            throw new  NullPointerException("condition is null");
        }
        this.condition = condition;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public Measurement getTemperature() { return this.temperature; }

    public Measurement getFeelsLikeTemperature() { return this.feelsLikeTemperature; }

    public Measurement getDewPoint() { return this.dewPoint; }

    public Measurement getHeatIndex() { return this.heatIndex; }

    public Measurement getWindChill() { return this.windChill; }

    public Measurement getVisibility() { return this.visibility; }

    public Measurement getAirPressure() { return this.airPressure; }

    public Percent getThunderstormProbability() { return this.thunderstormProbability; }

    public Percent getCloudCoverage() { return this.cloudCoverage; }

    public Percent getRelativeHumidity() { return this.relativeHumidity; }

    public int getUvIndex() { return this.uvIndex; }

    public Wind getWind() { return this.wind; }

    public Precipitation getPrecipitation() { return this.precipitation; }

    public WeatherCondition getCondition() { return this.condition; }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
                "Temperature: %s\n" +
                "Feels like: %s\n" +
                "Dew point: %s\n" +
                "Heat index: %s\n" +
                "Wind chill: %s\n" +
                "Visibility: %s\n" +
                "Air pressure: %s\n" +
                "Thunderstorm probability: %s\n" +
                "Cloud coverage: %s\n" +
                "Relative humidity: %s\n" +
                "UV Index: %d\n" +
                "%s\n" +
                "%s\n" +
                "%s",
                this.temperature,
                this.feelsLikeTemperature,
                this.dewPoint,
                this.heatIndex,
                this.windChill,
                this.visibility,
                this.airPressure,
                this.thunderstormProbability,
                this.cloudCoverage,
                this.relativeHumidity,
                this.uvIndex,
                this.wind,
                this.precipitation,
                this.condition
        );
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Weather)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Weather other = (Weather) object;
        return this.temperature.equals(other.temperature) &&
                this.feelsLikeTemperature.equals(other.feelsLikeTemperature) &&
                this.dewPoint.equals(other.dewPoint) &&
                this.heatIndex.equals(other.heatIndex) &&
                this.windChill.equals(other.windChill) &&
                this.visibility.equals(other.visibility) &&
                this.airPressure.equals(other.airPressure) &&
                this.thunderstormProbability.equals(other.thunderstormProbability) &&
                this.cloudCoverage.equals(other.cloudCoverage) &&
                this.relativeHumidity.equals(other.relativeHumidity) &&
                this.uvIndex == other.uvIndex &&
                this.wind.equals(other.wind) &&
                this.precipitation.equals(other.precipitation) &&
                this.condition.equals(other.condition);
    }
}
