package edu.uic.cs342.project2.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.uic.cs342.project2.backend.Utilities;
import edu.uic.cs342.project2.backend.json.weather.deserializers.DailyForecastsDeserializer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = DailyForecastsDeserializer.class)
public class DailyForecasts implements Iterable<DailyForecasts.DailyForecast> {
    // ── Forecast ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Forecast {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private Interval interval;

        private WeatherCondition condition;

        private Precipitation precipitation;

        private Wind wind;

        public double relativeHumidity;

        public int uvIndex;

        public double thunderstormProbability;

        public double cloudCover;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Forecast(Interval interval,
                        WeatherCondition condition,
                        Precipitation precipitation,
                        Wind wind,
                        double relativeHumidity,
                        int uvIndex,
                        double thunderstormProbability,
                        double cloudCover) throws NullPointerException {
            this.setInterval(interval);
            this.setCondition(condition);
            this.setPrecipitation(precipitation);
            this.setWind(wind);
            this.relativeHumidity = relativeHumidity;
            this.uvIndex = uvIndex;
            this.thunderstormProbability = thunderstormProbability;
            this.cloudCover = cloudCover;
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setInterval(Interval interval) throws NullPointerException {
            if (interval == null) {
                throw new NullPointerException("interval is null");
            }
            this.interval = interval;
        }

        public void setCondition(WeatherCondition condition) throws NullPointerException {
            if (condition == null) {
                throw new NullPointerException("condition is null");
            }
            this.condition = condition;
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

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public Interval getInterval() { return this.interval; }

        public WeatherCondition getCondition() { return this.condition; }

        public Precipitation getPrecipitation() { return this.precipitation; }

        public Wind getWind() { return this.wind; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Forecast)) {
                return false;
            }

            if (this == object) {
                return true;
            }

            Forecast other = (Forecast) object;
            return this.interval.equals(other.interval) &&
                    this.condition.equals(other.condition) &&
                    this.precipitation.equals(other.precipitation) &&
                    this.wind.equals(other.wind) &&
                    Utilities.doubleEquals(this.relativeHumidity, other.relativeHumidity) &&
                    this.uvIndex == other.uvIndex &&
                    Utilities.doubleEquals(this.thunderstormProbability, other.thunderstormProbability) &&
                    Utilities.doubleEquals(this.cloudCover, other.cloudCover);
        }
    }

    // ── Daily Forecast ───────────────────────────────────────────────────────────────────────────────────────────────
    public static class DailyForecast {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────
        private Forecast daytimeForecast, nighttimeForecast;

        private Temperature maxTemperature, minTemperature, maxFeelsLikeTemperature, minFeelsLikeTemperature;

        private ZonedDateTime sunriseTime, sunsetTime;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public DailyForecast(Forecast daytimeForecast,
                             Forecast nighttimeForecast,
                             Temperature maxTemperature,
                             Temperature minTemperature,
                             Temperature maxFeelsLikeTemperature,
                             Temperature minFeelsLikeTemperature,
                             ZonedDateTime sunriseTime,
                             ZonedDateTime sunsetTime) throws NullPointerException {
            this.setDaytimeForecast(daytimeForecast);
            this.setNighttimeForecast(nighttimeForecast);
            this.setMaxTemperature(maxTemperature);
            this.setMinTemperature(minTemperature);
            this.setMaxFeelsLikeTemperature(maxFeelsLikeTemperature);
            this.setMinFeelsLikeTemperature(minFeelsLikeTemperature);
            this.setSunriseTime(sunriseTime);
            this.setSunsetTime(sunsetTime);
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setDaytimeForecast(Forecast daytimeForecast) throws NullPointerException {
            if (daytimeForecast == null) {
                throw new NullPointerException("daytimeForecast is null");
            }
            this.daytimeForecast = daytimeForecast;
        }

        public void setNighttimeForecast(Forecast nighttimeForecast) throws NullPointerException {
            if (nighttimeForecast == null) {
                throw new NullPointerException("nighttimeForecast is null");
            }
            this.nighttimeForecast = nighttimeForecast;
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

        public void setMaxFeelsLikeTemperature(Temperature maxFeelsLikeTemperature) throws NullPointerException {
            if (maxFeelsLikeTemperature == null) {
                throw new NullPointerException("maxFeelsLikeTemperature is null");
            }
            this.maxFeelsLikeTemperature = maxFeelsLikeTemperature;
        }

        public void setMinFeelsLikeTemperature(Temperature minFeelsLikeTemperature) throws NullPointerException {
            if (minFeelsLikeTemperature == null) {
                throw new NullPointerException("minFeelsLikeTemperature is null");
            }
            this.minFeelsLikeTemperature = minFeelsLikeTemperature;
        }

        public void setSunriseTime(ZonedDateTime sunriseTime) throws NullPointerException {
            if (sunriseTime == null) {
                throw new NullPointerException("sunriseTime is null");
            }
            this.sunriseTime = sunriseTime;
        }

        public void setSunsetTime(ZonedDateTime sunsetTime) throws NullPointerException {
            if (sunsetTime == null) {
                throw new NullPointerException("sunsetTime is null");
            }
            this.sunsetTime = sunsetTime;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public Forecast getDaytimeForecast() { return this.daytimeForecast; }

        public Forecast getNighttimeForecast() { return this.nighttimeForecast; }

        public Temperature getMaxTemperature() { return this.maxTemperature; }

        public Temperature getMinTemperature() { return this.minTemperature; }

        public Temperature getMaxFeelsLikeTemperature() { return this.maxFeelsLikeTemperature; }

        public Temperature getMinFeelsLikeTemperature() { return this.minFeelsLikeTemperature; }

        public ZonedDateTime getSunriseTime() { return this.sunriseTime; }

        public ZonedDateTime getSunsetTime() { return this.sunsetTime; }

        // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────
        @Override
        public boolean equals(Object object) {
            if (!(object instanceof DailyForecast)) {
                return false;
            }

            if (this == object) {
                return true;
            }

            DailyForecast other = (DailyForecast) object;
            return this.daytimeForecast.equals(other.daytimeForecast) &&
                    this.nighttimeForecast.equals(other.nighttimeForecast) &&
                    this.minTemperature.equals(other.minTemperature) &&
                    this.maxTemperature.equals(other.maxTemperature) &&
                    this.minFeelsLikeTemperature.equals(other.minFeelsLikeTemperature) &&
                    this.maxFeelsLikeTemperature.equals(other.maxFeelsLikeTemperature) &&
                    this.sunriseTime.equals(other.sunriseTime) &&
                    this.sunsetTime.equals(other.sunsetTime);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private ZoneId zoneId;

    private ArrayList<DailyForecast> dailyForecasts;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public DailyForecasts(ZoneId zoneId, ArrayList<DailyForecast> dailyForecasts) throws NullPointerException {
        this.setZoneId(zoneId);
        this.setDailyForecasts(dailyForecasts);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setZoneId(ZoneId zoneId) throws NullPointerException {
        if (zoneId == null) {
            throw new NullPointerException("zoneId is null");
        }
        this.zoneId = zoneId;
    }

    public void setDailyForecasts(ArrayList<DailyForecast> dailyForecasts) throws NullPointerException {
        if (dailyForecasts == null) {
            throw new NullPointerException("dailyForecasts is null");
        }
        this.dailyForecasts = dailyForecasts;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public ZoneId getZoneId() { return this.zoneId; }

    public DailyForecast getForecast(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.dailyForecasts.size()) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + this.dailyForecasts.size());
        }
        return this.dailyForecasts.get(index);
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DailyForecasts)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        DailyForecasts other = (DailyForecasts) object;
        return this.dailyForecasts.equals(other.dailyForecasts);
    }

    @Override
    public Iterator<DailyForecast> iterator() { return this.dailyForecasts.iterator(); }
}
