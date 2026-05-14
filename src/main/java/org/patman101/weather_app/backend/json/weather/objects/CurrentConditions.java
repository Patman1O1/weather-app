package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.patman101.weather_app.backend.json.weather.deserializers.CurrentConditionsDeserializer;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CurrentConditionsDeserializer.class)
public class CurrentConditions {
    // ── History ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class History {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private Temperature temperatureChange, maxTemperature, minTemperature;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public History(Temperature temperatureChange, Temperature maxTemperature, Temperature minTemperature)
                throws NullPointerException {
            this.setTemperatureChange(temperatureChange);
            this.setMaxTemperature(maxTemperature);
            this.setMinTemperature(minTemperature);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setTemperatureChange(Temperature temperatureChange) throws NullPointerException {
            if (temperatureChange == null) {
                throw new NullPointerException("temperatureChange is null");
            }
            this.temperatureChange = temperatureChange;
        }

        public void setMaxTemperature(Temperature maxTemperature) throws NullPointerException {
            if (maxTemperature == null) {
                throw new NullPointerException("maxTemperature is null");
            }
            this.maxTemperature = maxTemperature;
        }

        public void setMinTemperature(Temperature minTemperature) throws NullPointerException {
            if (minTemperature == null) {
                throw new NullPointerException("minTemperature is null");
            }
            this.minTemperature = minTemperature;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public Temperature getTemperatureChange() { return this.temperatureChange; }

        public Temperature getMaxTemperature() { return this.maxTemperature; }

        public Temperature getMinTemperature() { return this.minTemperature; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof History)) {
                return false;
            }
            History other = (History) object;
            return this.temperatureChange.equals(other.temperatureChange) &&
                    this.maxTemperature.equals(other.maxTemperature) &&
                    this.minTemperature.equals(other.minTemperature);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private ZoneId timeZoneId;

    private WeatherCondition condition;

    private Temperature temperature, feelsLikeTemperature, dewPoint, heatIndex, windChill;

    private Precipitation precipitation;

    private Wind wind;

    private Visibility visibility;

    private History history;

    public boolean isDaytime;

    public double relativeHumidity;

    public int uvIndex;

    public double thunderstormProbability;

    public double cloudCover;

    public double airPressure;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public CurrentConditions(ZoneId timeZoneId,
                             WeatherCondition condition,
                             Temperature temperature,
                             Temperature feelsLikeTemperature,
                             Temperature dewPoint,
                             Temperature heatIndex,
                             Temperature windChill,
                             Precipitation precipitation,
                             Wind wind,
                             Visibility visibility,
                             History history,
                             boolean isDaytime,
                             double relativeHumidity,
                             int uvIndex,
                             double thunderstormProbability,
                             double cloudCover,
                             double airPressure) throws NullPointerException {
        this.setTimeZoneId(timeZoneId);
        this.setCondition(condition);
        this.setTemperature(temperature);
        this.setFeelsLikeTemperature(feelsLikeTemperature);
        this.setDewPoint(dewPoint);
        this.setHeatIndex(heatIndex);
        this.setWindChill(windChill);
        this.setWind(wind);
        this.setPrecipitation(precipitation);
        this.setVisibility(visibility);
        this.setHistory(history);
        this.isDaytime = isDaytime;
        this.relativeHumidity = relativeHumidity;
        this.uvIndex = uvIndex;
        this.thunderstormProbability = thunderstormProbability;
        this.cloudCover = cloudCover;
        this.airPressure = airPressure;
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setTimeZoneId(ZoneId timeZoneId) throws NullPointerException {
        if (timeZoneId == null) {
            throw new NullPointerException("timeZoneId is null");
        }
        this.timeZoneId = timeZoneId;
    }

    public void setCondition(WeatherCondition condition) throws NullPointerException {
        if (condition == null) {
            throw new NullPointerException("condition is null");
        }
        this.condition = condition;
    }

    public void setTemperature(Temperature temperature) throws NullPointerException {
        if (temperature == null) {
            throw new NullPointerException("temperature is null");
        }
        this.temperature = temperature;
    }

    public void setFeelsLikeTemperature(Temperature feelsLikeTemperature) throws NullPointerException {
        if (feelsLikeTemperature == null) {
            throw new NullPointerException("feelsLikeTemperature is null");
        }
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public void setDewPoint(Temperature dewPoint) throws NullPointerException {
        if (dewPoint == null) {
            throw new NullPointerException("dewPoint is null");
        }
        this.dewPoint = dewPoint;
    }

    public void setHeatIndex(Temperature heatIndex) throws NullPointerException {
        if (heatIndex == null) {
            throw new NullPointerException("heatIndex is null");
        }
        this.heatIndex = heatIndex;
    }

    public void setWindChill(Temperature windChill) throws NullPointerException {
        if (windChill == null) {
            throw new NullPointerException("windChill is null");
        }
        this.windChill = windChill;
    }

    public void setPrecipitation(Precipitation precipitation) throws NullPointerException {
        if (precipitation == null) {
            throw new NullPointerException("precipitation is null");
        }
        this.precipitation = precipitation;
    }

    public void setWind(Wind wind) throws NullPointerException {
        if (wind == null) {
            throw new NullPointerException("wind is null");
        }
        this.wind = wind;
    }

    public void setVisibility (Visibility visibility) throws NullPointerException {
        if (visibility == null) {
            throw new NullPointerException("visibility is null");
        }
        this.visibility = visibility;
    }

    public void setHistory(History history) throws NullPointerException {
        if (history == null) {
            throw new NullPointerException("history is null");
        }
        this.history = history;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public ZoneId getTimeZoneId() { return this.timeZoneId; }

    public WeatherCondition getCondition() { return this.condition; }

    public Temperature getTemperature() { return this.temperature; }

    public Temperature getFeelsLikeTemperature() { return this.feelsLikeTemperature; }

    public Temperature getDewPoint() { return this.dewPoint; }

    public Temperature getHeatIndex() { return this.heatIndex; }

    public Temperature getWindChill() { return this.windChill; }

    public Precipitation getPrecipitation() { return this.precipitation; }

    public Wind getWind() { return this.wind; }

    public Visibility getVisibility() { return this.visibility; }

    public History getHistory() { return this.history; }
}
