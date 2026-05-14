package org.patman101.weather_app.backend.json.weather.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.patman101.weather_app.backend.json.weather.deserializers.HourlyForecastsDeserializer;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = HourlyForecastsDeserializer.class)
public class HourlyForecasts implements Iterable<HourlyForecasts.Forecast> {
    // ── Forecast ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public static class Forecast {
        // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
        private Interval interval;

        private WeatherCondition weatherCondition;

        private Temperature temperature, feelsLikeTemperature, dewPoint, heatIndex, windChill;

        private Precipitation precipitation;

        private Wind wind;

        private Visibility visibility;

        public double humidity;

        public int uvIndex;

        public boolean isDaytime;

        public double thunderstormProbability;

        public double airPressure;

        public double cloudCover;

        // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────
        public Forecast(Interval interval,
                        WeatherCondition weatherCondition,
                        Temperature temperature,
                        Temperature feelsLikeTemperature,
                        Temperature dewPoint,
                        Temperature heatIndex,
                        Temperature windChill,
                        Precipitation precipitation,
                        Wind wind,
                        Visibility visibility,
                        double humidity,
                        int uvIndex,
                        boolean isDaytime,
                        double thunderstormProbability,
                        double airPressure,
                        double cloudCover) throws NullPointerException {
            this.setInterval(interval);
            this.setWeatherCondition(weatherCondition);
            this.setTemperature(temperature);
            this.setFeelsLikeTemperature(feelsLikeTemperature);
            this.setDewPoint(dewPoint);
            this.setHeatIndex(heatIndex);
            this.setWindChill(windChill);
            this.setPrecipitation(precipitation);
            this.setWind(wind);
            this.setVisibility(visibility);
            this.humidity = humidity;
            this.uvIndex = uvIndex;
            this.isDaytime = isDaytime;
            this.thunderstormProbability = thunderstormProbability;
            this.airPressure = airPressure;
            this.cloudCover = cloudCover;
        }

        // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public void setInterval(Interval interval) throws NullPointerException {
            if (interval == null) {
                throw new NullPointerException("interval is null");
            }
            this.interval = interval;
        }

        public void setWeatherCondition(WeatherCondition weatherCondition) throws NullPointerException {
            if (weatherCondition == null) {
                throw new NullPointerException("weatherCondition is null");
            }
            this.weatherCondition = weatherCondition;
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

        public void setVisibility(Visibility visibility) throws NullPointerException {
            if (visibility == null) {
                throw new NullPointerException("visibility is null");
            }
            this.visibility = visibility;
        }

        // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────
        public Interval getInterval() { return this.interval; }

        public WeatherCondition getWeatherCondition() { return this.weatherCondition; }

        public Temperature getTemperature() { return this.temperature; }

        public Temperature getFeelsLikeTemperature() { return this.feelsLikeTemperature; }

        public Temperature getDewPoint() { return this.dewPoint; }

        public Temperature getHeatIndex() { return this.heatIndex; }

        public Temperature getWindChill() { return this.windChill; }

        public Precipitation getPrecipitation() { return this.precipitation; }

        public Wind getWind() { return this.wind; }

        public Visibility getVisibility() { return this.visibility; }

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
                    this.weatherCondition.equals(other.weatherCondition) &&
                    this.temperature.equals(other.temperature) &&
                    this.feelsLikeTemperature.equals(other.feelsLikeTemperature) &&
                    this.dewPoint.equals(other.dewPoint) &&
                    this.heatIndex.equals(other.heatIndex) &&
                    this.precipitation.equals(other.precipitation) &&
                    this.wind.equals(other.wind) &&
                    this.visibility.equals(other.visibility);
        }
    }

    // ── Fields ───────────────────────────────────────────────────────────────────────────────────────────────────────
    private ZoneId zoneId;

    private ArrayList<Forecast> forecasts;

    // ── Constructors ─────────────────────────────────────────────────────────────────────────────────────────────────
    public HourlyForecasts(ZoneId zoneId, ArrayList<Forecast> forecasts) throws NullPointerException {
        this.setZoneId(zoneId);
        this.setForecasts(forecasts);
    }

    // ── Setters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public void setZoneId(ZoneId zoneId) throws NullPointerException {
        if (zoneId == null) {
            throw new NullPointerException("zoneId is null");
        }
        this.zoneId = zoneId;
    }

    public void setForecasts(ArrayList<Forecast> forecasts) throws NullPointerException {
        if (forecasts == null) {
            throw new NullPointerException("forecasts is null");
        }
        this.forecasts = forecasts;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────────────────────────────────────────
    public ZoneId getZoneId() { return this.zoneId; }

    public Forecast getForecast(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.forecasts.size()) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + this.forecasts.size());
        }
        return this.forecasts.get(index);
    }

    // ── Methods ──────────────────────────────────────────────────────────────────────────────────────────────────────
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HourlyForecasts)) {
            return false;
        }

        if (this == object) {
            return true;
        }

        HourlyForecasts other = (HourlyForecasts) object;
        return this.forecasts.equals(other.forecasts);
    }

    @Override
    public Iterator<Forecast> iterator() { return this.forecasts.iterator(); }
}
